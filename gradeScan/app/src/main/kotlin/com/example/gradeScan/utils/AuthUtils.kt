package com.example.gradeScan.utils

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.example.gradeScan.R

object AuthUtils {
    private lateinit var googleSignInClient: GoogleSignInClient

    // Initializes the Google Sign-In client with default sign-in options
    fun initializeGoogleSignInClient(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // Returns the Google Sign-In client instance
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        if (!::googleSignInClient.isInitialized) {
            initializeGoogleSignInClient(context)
        }
        return googleSignInClient
    }
}

