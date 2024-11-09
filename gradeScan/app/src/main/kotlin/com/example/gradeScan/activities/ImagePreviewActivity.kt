// java/com/example/docscanner/activities/ImagePreviewActivity.kt
package com.example.docscanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.docscanner.R
import com.example.docscanner.utils.ImageProcessor

class ImagePreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        val imagePaths = intent.getStringArrayListExtra("IMAGE_PATHS")
        val previewRecyclerView: RecyclerView = findViewById(R.id.rvPreviewImages)
        val confirmButton: Button = findViewById(R.id.btnConfirm)

        // Display images in RecyclerView (adapter required for full setup)
        // previewRecyclerView.adapter = PreviewAdapter(imagePaths)

        confirmButton.setOnClickListener {
            imagePaths?.forEach { path ->
                val processedBitmap = ImageProcessor.processImage(path)
                // Further processing for each image
            }
            // Navigate to ScannedImageDisplayActivity
            val intent = Intent(this, ScannedImageDisplayActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

