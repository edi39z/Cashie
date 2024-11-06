package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class activity_onboarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        // Inisialisasi ViewPager2
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        // Buat dan set adapter untuk ViewPager2
        val adapter = OnboardingAdapter(this)  // 'this' merujuk pada Context yang merupakan FragmentActivity
        viewPager.adapter = adapter

        // Optional: Menangani window inset untuk menghindari tumpang tindih dengan status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
