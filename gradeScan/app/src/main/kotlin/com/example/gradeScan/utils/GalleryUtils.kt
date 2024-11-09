// java/com/example/docscanner/utils/GalleryUtils.kt
package com.example.docscanner.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

object GalleryUtils {

    fun getGalleryIntent(): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    fun getImagePathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(projection[0])
        val filePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return filePath
    }
}
