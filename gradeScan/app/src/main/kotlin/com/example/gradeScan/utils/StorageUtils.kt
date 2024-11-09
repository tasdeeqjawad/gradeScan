package com.example.gradeScan.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

object StorageUtils {

    // Converts a URI to a Bitmap
    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
        return context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        } ?: throw IOException("Failed to decode URI to Bitmap")
    }

    // Saves a bitmap image to local storage with a unique file name
    fun saveImageToLocalStorage(context: Context, bitmap: Bitmap): Uri? {
        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        val directory = context.getExternalFilesDir(null) // Default external directory
        val imageFile = File(directory, fileName)

        return try {
            FileOutputStream(imageFile).use { output ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            }
            Uri.fromFile(imageFile)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // Retrieves a list of saved image files from local storage
    fun getSavedImages(context: Context): List<File> {
        val directory = context.getExternalFilesDir(null)
        return directory?.listFiles()?.filter { it.isFile && it.extension == "jpg" } ?: emptyList()
    }
}
