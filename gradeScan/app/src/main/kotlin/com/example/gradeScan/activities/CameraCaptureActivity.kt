package com.example.gradeScan.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.gradeScan.R
import com.example.gradeScan.utils.CameraUtils
import java.io.File

class CameraCaptureActivity : AppCompatActivity() {

    private val capturedImages = mutableListOf<Uri>()
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_capture)

        // Initialize buttons for capturing and batch processing
        val captureButton: Button = findViewById(R.id.btnCapture)
        val doneButton: Button = findViewById(R.id.btnDone)
        val keepScanningButton: Button = findViewById(R.id.btnKeepScanning)

        // Capture image on button click
        captureButton.setOnClickListener {
            openCameraForCapture()
        }

        // Finalize batch and navigate to ImagePreviewActivity
        doneButton.setOnClickListener {
            if (capturedImages.isNotEmpty()) {
                navigateToImagePreview()
            } else {
                Toast.makeText(this, "Please capture at least one image", Toast.LENGTH_SHORT).show()
            }
        }

        // Keep adding images to the batch for multi-capture
        keepScanningButton.setOnClickListener {
            openCameraForCapture()
        }
    }

    // Opens the camera to capture an image with an A4 overlay
    private fun openCameraForCapture() {
        val photoFile: File? = CameraUtils.createImageFile(this)
        photoFile?.also {
            currentPhotoPath = it.absolutePath
            val photoURI: Uri = CameraUtils.getUriForFile(this, it)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraLauncher.launch(cameraIntent)
        }
    }

    // Handles the result from the camera intent
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = Uri.fromFile(File(currentPhotoPath))
            capturedImages.add(imageUri)
            Toast.makeText(this, "Image captured successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Capture cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigates to ImagePreviewActivity with captured images
    private fun navigateToImagePreview() {
        val intent = Intent(this, ImagePreviewActivity::class.java)
        intent.putParcelableArrayListExtra("CAPTURED_IMAGES", ArrayList(capturedImages))
        startActivity(intent)
    }
}
