package com.example.snackshield.common.util

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat

object ActivityAndPermission {

    fun Activity.openAppSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
    }


    fun checkAndRequestPermission(
        context: Context,
        permission: String,
        launcher: ManagedActivityResultLauncher<String, Boolean>,
        onGranted: () -> Unit = {}
    ) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
        if (permissionCheckResult == PERMISSION_GRANTED) {
            onGranted()
        } else {
            launcher.launch(permission)
        }
    }

    fun checkAndRequestGalleryPermission(
        context: Context,
        permissionList: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
        onResult: (StorageAccess) -> Unit = {}
    ) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            (
                    ContextCompat.checkSelfPermission(
                        context,
                        READ_MEDIA_IMAGES
                    ) == PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(
                                context,
                                READ_MEDIA_VIDEO
                            ) == PERMISSION_GRANTED
                    )
        ) {
            onResult(StorageAccess.Full)
        } else if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            ContextCompat.checkSelfPermission(
                context,
                READ_MEDIA_VISUAL_USER_SELECTED
            ) == PERMISSION_GRANTED
        ) {
            onResult(StorageAccess.Partial)
        } else if (ContextCompat.checkSelfPermission(
                context,
                READ_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED
        ) {
            onResult(StorageAccess.Full)
        } else {
            launcher.launch(permissionList)
        }

    }
}

enum class StorageAccess {
    Checking ,NotAllowed  , Partial , Full, None
}
enum class Permission {
    Allowed, NotAllowed, Checking
}