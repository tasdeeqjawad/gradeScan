// java/com/example/docscanner/activities/ImageSelectionActivity.kt
package com.example.docscanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.docscanner.R

class ImageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selection)

        val confirmSelectionButton: Button = findViewById(R.id.btnConfirmSelection)
        confirmSelectionButton.setOnClickListener {
            // Passing selected image(s) to ImagePreviewActivity
            val intent = Intent(this, ImagePreviewActivity::class.java)
            startActivity(intent)
        }
    }
}
