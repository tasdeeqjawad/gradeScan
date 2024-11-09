package com.example.gradeScan.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.tasks.await

object OCRUtils {
    private val recognizer: TextRecognizer = TextRecognition.getClient()

    // Extracts text from an image given its URI
    suspend fun extractTextFromImage(context: Context, imageUri: Uri): String {
        val bitmap = StorageUtils.getBitmapFromUri(context, imageUri)
        return extractTextFromBitmap(bitmap)
    }

    // Extracts text from a bitmap using ML Kit Text Recognition
    suspend fun extractTextFromBitmap(bitmap: Bitmap): String {
        val image = InputImage.fromBitmap(bitmap, 0)
        return try {
            val result: Text = recognizer.process(image).await()
            result.text  // Returns the recognized text as a single String
        } catch (e: Exception) {
            ""
        }
    }
}
