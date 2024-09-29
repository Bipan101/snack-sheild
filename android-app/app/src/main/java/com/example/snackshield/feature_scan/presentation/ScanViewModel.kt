package com.example.snackshield.feature_scan.presentation

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment
import android.os.FileUtils.copy
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snackshield.common.domain.repo.SessionManager
import com.example.snackshield.feature_scan.domain.model.IngredientData
import com.example.snackshield.feature_scan.domain.model.NutrientRequest
import com.example.snackshield.feature_scan.domain.repo.ScanRepo
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanRepo: ScanRepo,
    private val sessionManager: SessionManager
) : ViewModel() {
    companion object {
        const val TAG = "SCAN_VIEWMODEL"
    }

    private val _scanState = MutableStateFlow<ScanUiState>(ScanUiState.Remaining)
    val scanState: StateFlow<ScanUiState> = _scanState.asStateFlow()
    private val _state = MutableStateFlow(ScanState())
    val state: StateFlow<ScanState> = _state.asStateFlow()
    fun onEvent(event: ScanEvent) {
        when (event) {
            is ScanEvent.GetDataFromBarcode -> getDataFromBarcode(event.barcode)
            is ScanEvent.Recommend -> getRecommendation(event.nutrientRequest)
            is ScanEvent.DetectFromImage -> detectFromImage(event.imageUri,event.context)
            is ScanEvent.DetectFromLabel -> detectFromLabel(event.ingredientData)
            is ScanEvent.SaveImage -> saveImage(event.imageUri)
        }
    }

    fun resetState() {
        _scanState.value = ScanUiState.Remaining
        _state.value = ScanState()
    }

    private fun saveImage(imageUri: Uri) {
        _state.update { it.copy(imageUri = imageUri) }
    }
    private fun detectFromLabel(ingredientData: String) {
        _scanState.value = ScanUiState.Loading
        val userId = sessionManager.getUser()!!.id
        viewModelScope.launch {
            try {
                val response = scanRepo.detectFromLabel(ingredientData,userId)
                Log.d(TAG, "detectFromLabel: $response")
                if (response != null) {
                    _scanState.value = ScanUiState.Success
                    _state.update { it.copy(detectFromLabel = response) }
                } else {
                    _scanState.value = ScanUiState.Error("Response is null")
                }
            } catch (e: IOException) {
                _scanState.value = e.message?.let { ScanUiState.Error(it) }
                    .run { ScanUiState.Error("Internal error") }
                Log.e(TAG, "detectFromLabel: $e")
            }
        }
    }
    private fun detectFromImage(imageUri: Uri,context: Context) {
        _scanState.value = ScanUiState.Loading
        val userId = sessionManager.getUser()!!.id
        Log.d(TAG, "detectFromImage: $imageUri")
        val imageFile = fileFromContentUri(context, imageUri)
        Log.d(TAG, "detectFromImage: $imageFile")
        viewModelScope.launch {
            try {
                val response = scanRepo.uploadImageForDetection(userId, imageFile)
                Log.d(TAG, "detectFromImage: $response")
                if (response != null) {
                    _scanState.value = ScanUiState.Success
                    _state.update { it.copy(detectFromImage = response) }
                } else {
                    _scanState.value = ScanUiState.Error("Response is null")
                }
            } catch (e: IOException) {
                _scanState.value = e.message?.let { ScanUiState.Error(it) }
                    .run { ScanUiState.Error("Internal error") }
                Log.e(TAG, "detectFromImage: $e")
            }
        }
    }

    private fun getDataFromBarcode(barcode: String) {
        _scanState.value = ScanUiState.Loading
        val userId = sessionManager.getUser()!!.id
        Log.d(TAG, "getDataFromBarcode: $userId")
        viewModelScope.launch {
            try {
                val response = scanRepo.barcode(barcode,userId)
                Log.d(TAG, "getDataFromBarcode: $response")
                if (response != null) {
                    _scanState.value = ScanUiState.Success
                    _state.update { it.copy(productFormBarcode = response) }
                } else {
                    _scanState.value = ScanUiState.Error("Response is null")
                }
            } catch (e: IOException) {
                _scanState.value = e.message?.let { ScanUiState.Error(it) }
                    .run { ScanUiState.Error("Internal error") }
                Log.e(TAG, "getDataFromBarcode: $e")
            }
        }
    }

    private fun getRecommendation(nutrientRequest: NutrientRequest) {
        _scanState.value = ScanUiState.Loading
        Log.d(TAG, "getRecommendation: $nutrientRequest")
        viewModelScope.launch {
            try {
                val response = scanRepo.recipeRecommend(nutrientRequest)
                Log.d(TAG, "getRec: $response")
                if (response != null) {
                    _scanState.value = ScanUiState.Success
                    _state.update { it.copy(recommendedProduct = response) }
                } else {
                    _scanState.value = ScanUiState.Error("Response is null")
                }
            } catch (e: IOException) {
                _scanState.value = e.message?.let { ScanUiState.Error(it) }
                    .run { ScanUiState.Error("Internal error") }
                Log.e(TAG, "getRec: $e")
            }
        }
    }

    fun getImageUri(context: Context): Uri? {
        val file = createImageFile(context = context)
        val imageUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        return imageUri
    }

    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", //prefix
            ".jpg", //suffix
            storageDir //directory
        )
    }

    fun getImageFromUri(context: Context, uri: Uri): InputImage {
        val image: InputImage = InputImage.fromFilePath(context, uri)
        return image
    }

    private fun fileFromContentUri(context: Context, contentUri: Uri): File {

        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temporary_file" + if (fileExtension != null) ".$fileExtension" else ""

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                copy(inputStream, oStream)
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }

}