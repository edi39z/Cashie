package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // Pindah ke OnboardingActivity setelah 3 detik
        Handler().postDelayed({
            val intent = Intent(this, activity_onboarding::class.java)
            startActivity(intent)
            finish() // Menutup LoadingActivity agar tidak bisa kembali ke halaman ini
        }, 3000) // 3000ms = 3 detik
    }
}
