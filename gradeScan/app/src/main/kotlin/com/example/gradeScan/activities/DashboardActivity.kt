// java/com/example/docscanner/activities/DashboardActivity.kt
package com.example.docscanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.docscanner.R
import com.example.docscanner.utils.StorageUtils

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val newScanButton: Button = findViewById(R.id.btnNewScan)
        val scansRecyclerView: RecyclerView = findViewById(R.id.rvScans)

        // Load past scans and display them in RecyclerView
        val savedScans = StorageUtils.getSavedImages(this)
        // Set up RecyclerView Adapter to display savedScans

        newScanButton.setOnClickListener {
            val intent = Intent(this, ImageSourceActivity::class.java)
            startActivity(intent)
        }

        // Open scan when clicked (Placeholder: actual adapter item click listener required)
        // scansRecyclerView.adapter = ScanAdapter(savedScans) { scanFile ->
        //     val intent = Intent(this, ScannedImageDisplayActivity::class.java)
        //     intent.putExtra("SCAN_PATH", scanFile.absolutePath)
        //     startActivity(intent)
        // }
    }
}
