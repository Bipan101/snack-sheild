package com.example.snackshield.feature_scan.presentation

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import java.io.File
import java.util.Date

const val TAG = "ScanViewModel"

class ScanViewModel : ViewModel() {

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
}