package com.example.gradeScan.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gradeScan.R
import com.example.gradeScan.adapters.ImagePreviewAdapter

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imagePreviewAdapter: ImagePreviewAdapter
    private val imageUris = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        val confirmButton: Button = findViewById(R.id.btnConfirm)
        imagesRecyclerView = findViewById(R.id.rvImagePreview)

        // Retrieve selected or captured images from intent extras
        val selectedImages = intent.getParcelableArrayListExtra<Uri>("SELECTED_IMAGES") ?: arrayListOf()
        val capturedImages = intent.getParcelableArrayListExtra<Uri>("CAPTURED_IMAGES") ?: arrayListOf()

        imageUris.addAll(selectedImages)
        imageUris.addAll(capturedImages)

        if (imageUris.isEmpty()) {
            Toast.makeText(this, "No images to preview", Toast.LENGTH_SHORT).show()
            finish()  // Close activity if no images are available
        } else {
            setupRecyclerView()
        }

        // Confirm selection and navigate to ScannedImageDisplayActivity
        confirmButton.setOnClickListener {
            navigateToScannedImageDisplay()
        }
    }

    // Sets up RecyclerView to display the preview of selected or captured images
    private fun setupRecyclerView() {
        imagePreviewAdapter = ImagePreviewAdapter(imageUris)
        imagesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imagesRecyclerView.adapter = imagePreviewAdapter
    }

    // Navigates to ScannedImageDisplayActivity with the confirmed images
    private fun navigateToScannedImageDisplay() {
        val intent = Intent(this, ScannedImageDisplayActivity::class.java)
        intent.putParcelableArrayListExtra("CONFIRMED_IMAGES", ArrayList(imageUris))
        startActivity(intent)
    }
}

