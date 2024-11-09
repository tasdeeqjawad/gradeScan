// java/com/example/docscanner/activities/ScannedImageDisplayActivity.kt
package com.example.docscanner.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.docscanner.R

class ScannedImageDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_image_display)

        val extractTextButton: Button = findViewById(R.id.btnExtractText)
        val saveImageButton: Button = findViewById(R.id.btnSaveImage)

        extractTextButton.setOnClickListener {
            // Code to extract text from image
        }

        saveImageButton.setOnClickListener {
            // Code to save the image locally
        }
    }
}
