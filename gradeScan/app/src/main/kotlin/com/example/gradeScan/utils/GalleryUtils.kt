package com.example.gradeScan.utils

import android.content.Intent
import android.provider.MediaStore

object GalleryUtils {
    // Creates an intent to open the gallery for multi-image selection
    fun createGalleryIntent(): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        return intent
    }
}

