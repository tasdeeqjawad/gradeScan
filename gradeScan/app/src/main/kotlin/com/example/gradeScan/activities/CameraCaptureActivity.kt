// java/com/example/docscanner/activities/CameraCaptureActivity.kt
package com.example.docscanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.docscanner.R
import com.example.docscanner.utils.CameraUtils

class CameraCaptureActivity : AppCompatActivity() {

    private val capturedImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_capture)

        val captureButton: Button = findViewById(R.id.btnCapture)
        val doneButton: Button = findViewById(R.id.btnDone)
        val keepScanningButton: Button = findViewById(R.id.btnKeepScanning)

        captureButton.setOnClickListener {
            val imagePath = CameraUtils.captureImageWithOverlay(this)
            capturedImages.add(imagePath)
        }

        doneButton.setOnClickListener {
            val intent = Intent(this, ImagePreviewActivity::class.java)
            intent.putStringArrayListExtra("IMAGE_PATHS", ArrayList(capturedImages))
            startActivity(intent)
            finish()
        }

        keepScanningButton.setOnClickListener {
            // Allows for additional captures to be added to capturedImages list
        }
    }
}
