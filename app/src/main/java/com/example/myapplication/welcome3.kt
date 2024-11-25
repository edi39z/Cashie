package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class welcome3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome3)

        // Navigasi ke welcome4 saat root layout diklik
        findViewById<View>(R.id.main).setOnClickListener {
            Intent(this, welcome4::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}
