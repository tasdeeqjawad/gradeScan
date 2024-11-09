// java/com/example/docscanner/activities/ImageSourceActivity.kt
package com.example.docscanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.docscanner.R

class ImageSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_source)

        val uploadButton: Button = findViewById(R.id.btnUpload)
        val captureButton: Button = findViewById(R.id.btnCapture)

        uploadButton.setOnClickListener {
            // Navigate to ImageSelectionActivity
            val intent = Intent(this, ImageSelectionActivity::class.java)
            startActivity(intent)
        }

        captureButton.setOnClickListener {
            // Navigate to CameraCaptureActivity
            val intent = Intent(this, CameraCaptureActivity::class.java)
            startActivity(intent)
        }
    }
}
