package com.example.gradeScan.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CameraUtils {
    // Creates a temporary image file for storing the captured photo
    fun createImageFile(context: Context): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(null)
        return try {
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        } catch (ex: IOException) {
            null
        }
    }

    // Returns a URI for the specified file, required for camera intent
    fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }
}
