package com.example.gradeScan.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.gradeScan.R

class ImageSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_source)

        // Initialize buttons for upload and capture options
        val uploadButton: Button = findViewById(R.id.btnUpload)
        val captureButton: Button = findViewById(R.id.btnCapture)

        // Set click listener for upload option
        uploadButton.setOnClickListener {
            openImageSelection()
        }

        // Set click listener for capture option
        captureButton.setOnClickListener {
            openCameraCapture()
        }
    }

    // Opens ImageSelectionActivity to allow user to pick images from storage
    private fun openImageSelection() {
        val intent = Intent(this, ImageSelectionActivity::class.java)
        startActivity(intent)
    }

    // Opens CameraCaptureActivity to allow user to take a new picture
    private fun openCameraCapture() {
        val intent = Intent(this, CameraCaptureActivity::class.java)
        startActivity(intent)
    }
}

