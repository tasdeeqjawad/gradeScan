package com.example.gradeScan.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gradeScan.R
import com.example.gradeScan.adapters.ScannedImageAdapter
import com.example.gradeScan.utils.OCRUtils
import com.example.gradeScan.utils.StorageUtils

class ScannedImageDisplayActivity : AppCompatActivity() {

    private lateinit var scannedImagesRecyclerView: RecyclerView
    private lateinit var scannedImageAdapter: ScannedImageAdapter
    private val scannedImageUris = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_image_display)

        val extractTextButton: Button = findViewById(R.id.btnExtractText)
        val saveImageButton: Button = findViewById(R.id.btnSaveImage)
        scannedImagesRecyclerView = findViewById(R.id.rvScannedImages)

        // Retrieve confirmed images from the previous activity
        val confirmedImages = intent.getParcelableArrayListExtra<Uri>("CONFIRMED_IMAGES") ?: arrayListOf()
        scannedImageUris.addAll(confirmedImages)

        if (scannedImageUris.isEmpty()) {
            Toast.makeText(this, "No images to display", Toast.LENGTH_SHORT).show()
            finish()  // Close activity if no images are available
        } else {
            setupRecyclerView()
        }

        // Extract text (OCR) when button is clicked
        extractTextButton.setOnClickListener {
            performOCR()
        }

        // Save images to local storage when button is clicked
        saveImageButton.setOnClickListener {
            saveScannedImages()
        }
    }

    // Sets up RecyclerView to display the scanned images
    private fun setupRecyclerView() {
        scannedImageAdapter = ScannedImageAdapter(scannedImageUris)
        scannedImagesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        scannedImagesRecyclerView.adapter = scannedImageAdapter
    }

    // Performs OCR on each image and displays the extracted text
    private fun performOCR() {
        if (scannedImageUris.isNotEmpty()) {
            val extractedTexts = mutableListOf<String>()
            for (imageUri in scannedImageUris) {
                val extractedText = OCRUtils.extractTextFromImage(this, imageUri)
                extractedTexts.add(extractedText)
            }
            // Display extracted text (you can modify to show in a dialog or new activity)
            Toast.makeText(this, "Extracted Texts: ${extractedTexts.joinToString(", ")}", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "No images available for OCR", Toast.LENGTH_SHORT).show()
        }
    }

    // Saves scanned images to local storage
    private fun saveScannedImages() {
        if (scannedImageUris.isNotEmpty()) {
            for (imageUri in scannedImageUris) {
                StorageUtils.saveImageToLocalStorage(this, imageUri)
            }
            Toast.makeText(this, "Images saved successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No images available to save", Toast.LENGTH_SHORT).show()
        }
    }
}
