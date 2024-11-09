// java/com/example/docscanner/utils/CameraUtils.kt
package com.example.docscanner.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CameraUtils {

    fun getCameraIntent(context: Context): Intent {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile(context)
        val photoURI: Uri = FileProvider.getUriForFile(
            context, "${context.packageName}.provider", photoFile
        )
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI)
        return intent
    }

    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}
