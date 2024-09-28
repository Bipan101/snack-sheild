package com.example.snackshield.feature_scan.presentation.screen

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.snackshield.common.components.AppTextField
import com.example.snackshield.common.components.AppTopBar
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.common.util.ActivityAndPermission
import com.example.snackshield.common.util.Permission
import com.example.snackshield.common.util.StorageAccess
import com.example.snackshield.feature_auth.presentation.components.SubmitButton
import com.example.snackshield.feature_scan.presentation.ScanViewModel
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun ScanTextScreen(viewModel: ScanViewModel, goBack: () -> Unit, toResponse : () -> Unit) {
    val context = LocalContext.current
    var storageAccess by remember {
        mutableStateOf(StorageAccess.Checking)
    }
    var cameraAccess by remember {
        mutableStateOf(Permission.Checking)
    }
    var imageUri: Uri by rememberSaveable {
        mutableStateOf(Uri.EMPTY)
    }
    var itemName by rememberSaveable {
        mutableStateOf("")
    }
    var scannedText by remember {
        mutableStateOf("")
    }
    //Gallery
    val mediaPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_VISUAL_USER_SELECTED)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO)
    } else {
        arrayOf(READ_EXTERNAL_STORAGE)
    }

    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { mediaPermissionMap ->
        storageAccess = when {
            !mediaPermissionMap.containsValue(true) -> {
                StorageAccess.None
            }

            (mediaPermissionMap[READ_MEDIA_VISUAL_USER_SELECTED] == true) && (mediaPermissionMap[READ_MEDIA_IMAGES] == false && mediaPermissionMap[READ_MEDIA_VIDEO] == false) -> {
                StorageAccess.Partial
            }

            else -> {
                StorageAccess.Full
            }
        }
    }
    val getMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )
    val checkAndLaunchGalleryPermission = {
        ActivityAndPermission.checkAndRequestGalleryPermission(
            context = context,
            permissionList = mediaPermission,
            launcher = mediaPermissionLauncher
        ) {
            storageAccess = it
            if (storageAccess == StorageAccess.Full || storageAccess == StorageAccess.Partial) {
                getMedia.launch("image/*")
            }
        }
    }
    val checkGalleryPermission = {
        ActivityAndPermission.checkAndRequestGalleryPermission(
            context = context,
            permissionList = mediaPermission,
            launcher = mediaPermissionLauncher
        ) {
            storageAccess = it
        }
    }

    //Camera

    val photoUri: Uri? = viewModel.getImageUri(context)
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        cameraAccess = if (permissionGranted) {
            Permission.Allowed
        } else {
            Permission.NotAllowed
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                if (photoUri != null) {
                    imageUri = photoUri
                }
            }
        }
    )


    val checkAndLaunchCameraPermission = {
        ActivityAndPermission.checkAndRequestPermission(
            context = context,
            permission = CAMERA,
            launcher = cameraPermissionLauncher
        ) {
            cameraAccess = Permission.Allowed
            if (photoUri != null) {
                cameraLauncher.launch(photoUri)
            }
        }
    }
    val checkCameraPermission = {
        ActivityAndPermission.checkAndRequestPermission(
            context = context,
            permission = CAMERA,
            launcher = cameraPermissionLauncher
        ) {
            cameraAccess = Permission.Allowed
        }
    }
    LaunchedEffect(true) {
        checkCameraPermission()
        checkGalleryPermission()
    }
    // Text recognizer initialization
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    Column {
        AppTopBar(identifier = "Scan label") {
            goBack()
            viewModel.resetState()
        }
        ScanTextView(
            itemName,
            changeItemName = { itemName = it },
            imageUri,
            checkAndLaunchCameraPermission,
            checkAndLaunchGalleryPermission,
            textScan = {
                // Process image for text recognition
                recognizer.process(viewModel.getImageFromUri(context, imageUri))
                    .addOnSuccessListener { visionText ->
                        Log.d(TAG, "ScanScreen (Text): ${visionText.text}")
                        // Handle text recognition result here
                        scannedText = visionText.text
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "ScanScreen (Text): Failure", e)
                        scannedText = "No text found to scan."
                    }
            },
            scannedText,
        )
    }

}

@Composable
fun ScanTextView(
    itemName: String,
    changeItemName: (String) -> Unit,
    imageUri: Uri?,
    checkCameraPermission: () -> Unit,
    checkGalleryPermission: () -> Unit,
    textScan: () -> Unit,
    scannedText: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            AppTextField(
                identifier = "Snack Name",
                value = itemName,
                onValueChange = changeItemName,
                placeholder = {
                    Text("Enter item name...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = "itemName",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            )
            Spacing(height = 12)
            TextSnackField(
                identifier = "Snack label image",
                value = imageUri,
                checkGalleryPermission,
                checkCameraPermission,
                textScan
            )
            Spacing(height = 12)
            if (scannedText.isNotEmpty()) {
                AppTextField(
                    identifier = "Snack label detail",
                    value = scannedText,
                    onValueChange = {},
                    placeholder = {},
                    readOnly = true
                )
            }
            Spacing(height = 24)
            SubmitButton(text = "Done") {

            }
        }
    }
}

@Composable
fun TextSnackField(
    identifier: String = "",
    value: Uri?,
    checkGalleryPermission: () -> Unit,
    checkCameraPermission: () -> Unit,
    textScan: () -> Unit
) {
    Text(
        text = identifier, style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
    Spacing(height = 8)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(12.dp)
    ) {
        TextSnackFieldOptions(
            textScan,
            checkCameraPermission,
            checkGalleryPermission,
            value
        )
    }
}

@Composable
fun TextSnackFieldOptions(
    textScan: () -> Unit,
    checkCameraPermission: () -> Unit,
    checkGalleryPermission: () -> Unit,
    value: Uri?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Camera,
            contentDescription = "camera",
            modifier = Modifier
                .size(32.dp)
                .clickable { checkCameraPermission() })
        Spacing(width = 8)
        Icon(
            imageVector = Icons.Default.Photo,
            contentDescription = "photo",
            modifier = Modifier
                .size(32.dp)
                .clickable { checkGalleryPermission() })
    }
    Spacing(height = 12)
    Log.d("ImageBuilder", "ScanFieldOptions: $value")
    if (value != Uri.EMPTY) {
        Image(
            painter = rememberAsyncImagePainter(model = value),
            contentDescription = "image",
            modifier = Modifier
                .padding(4.dp)
                .size(180.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        MoreTextScanFieldOptions(
            textScan
        )
    }
}

@Composable
fun MoreTextScanFieldOptions(textScan: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.TextFields,
            contentDescription = "scan",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    textScan.invoke()
                })
    }
}