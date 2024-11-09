package com.example.gradeScan.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gradeScan.R
import com.example.gradeScan.adapters.ImageSelectionAdapter

class ImageSelectionActivity : AppCompatActivity() {

    private lateinit var selectedImagesRecyclerView: RecyclerView
    private lateinit var imageSelectionAdapter: ImageSelectionAdapter
    private val selectedImages = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selection)

        val confirmSelectionButton: Button = findViewById(R.id.btnConfirmSelection)
        selectedImagesRecyclerView = findViewById(R.id.rvSelectedImages)

        // Initialize RecyclerView to display selected images
        setupRecyclerView()

        // Launch gallery to select images
        openGalleryForSelection()

        // Confirm selection and navigate to ImagePreviewActivity
        confirmSelectionButton.setOnClickListener {
            if (selectedImages.isNotEmpty()) {
                navigateToImagePreview()
            } else {
                Toast.makeText(this, "Please select at least one image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        imageSelectionAdapter = ImageSelectionAdapter(selectedImages)
        selectedImagesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        selectedImagesRecyclerView.adapter = imageSelectionAdapter
    }

    // Opens the gallery for the user to select images
    private fun openGalleryForSelection() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galleryLauncher.launch(intent)
    }

    // Handles the result of image selection from gallery
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                if (data.clipData != null) { // Multiple images selected
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        selectedImages.add(imageUri)
                    }
                } else if (data.data != null) { // Single image selected
                    val imageUri = data.data!!
                    selectedImages.add(imageUri)
                }
                imageSelectionAdapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(this, "No images selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigates to ImagePreviewActivity with the selected images
    private fun navigateToImagePreview() {
        val intent = Intent(this, ImagePreviewActivity::class.java)
        intent.putParcelableArrayListExtra("SELECTED_IMAGES", ArrayList(selectedImages))
        startActivity(intent)
    }
}
