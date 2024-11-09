package com.example.gradeScan.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gradeScan.R
import com.example.gradeScan.utils.StorageUtils
import com.example.gradeScan.adapters.ScanAdapter

class DashboardActivity : AppCompatActivity() {

    private lateinit var scansRecyclerView: RecyclerView
    private lateinit var scanAdapter: ScanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val newScanButton: Button = findViewById(R.id.btnNewScan)
        scansRecyclerView = findViewById(R.id.rvScans)

        // Load past scans from storage
        val savedScans = StorageUtils.getSavedImages(this)

        // Set up RecyclerView with ScanAdapter
        scanAdapter = ScanAdapter(savedScans) { scanFile ->
            openScan(scanFile.absolutePath)
        }
        scansRecyclerView.layoutManager = LinearLayoutManager(this)
        scansRecyclerView.adapter = scanAdapter

        newScanButton.setOnClickListener {
            val intent = Intent(this, ImageSourceActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openScan(scanPath: String) {
        val intent = Intent(this, ScannedImageDisplayActivity::class.java)
        intent.putExtra("SCAN_PATH", scanPath)
        startActivity(intent)
    }
}
