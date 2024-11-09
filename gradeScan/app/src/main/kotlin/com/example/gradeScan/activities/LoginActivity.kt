// java/com/example/gradeScan/activities/LoginActivity.kt
package com.example.gradeScan.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.docscanner.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Login button functionality
        val loginButton: Button = findViewById(R.id.btnLogin)
        loginButton.setOnClickListener {
            // Navigate to Dashboard after login
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Close LoginActivity
        }
    }
}
