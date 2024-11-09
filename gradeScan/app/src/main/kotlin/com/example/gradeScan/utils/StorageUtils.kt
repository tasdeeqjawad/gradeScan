// java/com/example/docscanner/utils/StorageUtils.kt
package com.example.docscanner.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

object StorageUtils {

    fun saveImageToStorage(context: Context, bitmap: Bitmap, filename: String): String {
        val file = File(context.filesDir, "$filename.jpg")
        FileOutputStream(file).use { output ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
        }
        return file.absolutePath
    }

    fun getSavedImages(context: Context): List<File> {
        val dir = context.filesDir
        return dir.listFiles { file -> file.extension == "jpg" }?.toList() ?: emptyList()
    }
}
