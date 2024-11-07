package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class activity_onboarding : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        // Inisialisasi ViewPager2 dan DotsIndicator
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val dotsIndicator: DotsIndicator = findViewById(R.id.dotsIndicator)

        // Buat dan set adapter untuk ViewPager2
        val adapter = OnboardingAdapter(this)  // Ganti dengan adapter Anda sendiri
        viewPager.adapter = adapter

        // Hubungkan DotsIndicator dengan ViewPager2
        dotsIndicator.setViewPager2(viewPager)

        // Optional: Menangani window inset untuk menghindari tumpang tindih dengan status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Menambahkan animasi kustom dengan PageTransformer
        viewPager.setPageTransformer { page, position ->
            val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.alpha = 0.3f + (1 - Math.abs(position)) * 0.7f
        }
    }
}
