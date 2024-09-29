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
import androidx.compose.runtime.collectAsState
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
import com.example.snackshield.feature_scan.presentation.ScanEvent
import com.example.snackshield.feature_scan.presentation.ScanUiState
import com.example.snackshield.feature_scan.presentation.ScanViewModel
import java.io.File

@Composable
fun ScanFoodScreen(viewModel: ScanViewModel, goBack: () -> Unit, toResponse : () -> Unit) {
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
    val event = viewModel::onEvent
    val scanState by viewModel.scanState.collectAsState()
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

    Column {
        AppTopBar(identifier = "Scan food") {
            goBack()
            viewModel.resetState()
        }
        ScanFoodView(
            itemName,
            changeItemName = { itemName = it },
            imageUri,
            checkAndLaunchCameraPermission,
            checkAndLaunchGalleryPermission,
            onSubmit = {
                event.invoke(ScanEvent.SaveImage(imageUri))
                event.invoke(ScanEvent.DetectFromImage(imageUri,context))
            },
            scanState,
            toResponse
        )
    }

}

@Composable
fun ScanFoodView(
    itemName: String,
    changeItemName: (String) -> Unit,
    imageUri: Uri?,
    checkCameraPermission: () -> Unit,
    checkGalleryPermission: () -> Unit,
    onSubmit : () -> Unit,
    scanState: ScanUiState,
    toResponse: () -> Unit
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
            FoodSnackField(
                identifier = "Snack image",
                value = imageUri,
                checkGalleryPermission,
                checkCameraPermission,
            )
            Spacing(height = 24)
            when(scanState) {
                is ScanUiState.Error ->  SubmitButton(text = scanState.error) {
                onSubmit()
                }
                ScanUiState.Loading ->  SubmitButton(text = "Loading..") {
                }
                ScanUiState.Remaining ->  SubmitButton(text = "Done") {
                    onSubmit()
                }
                ScanUiState.Success -> {
                    SubmitButton(text = "Done") {

                    }
                    toResponse()
                }
            }

        }
    }
}

@Composable
fun FoodSnackField(
    identifier: String = "",
    value: Uri?,
    checkGalleryPermission: () -> Unit,
    checkCameraPermission: () -> Unit,
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
        FoodSnackFieldOptions(
            checkCameraPermission,
            checkGalleryPermission,
            value
        )
    }
}

@Composable
fun FoodSnackFieldOptions(
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
    }
}
