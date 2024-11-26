package com.example.myapplication.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.welcome1

@SuppressLint("Loading")
class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Handler(Looper.getMainLooper()).postDelayed({
            goToWelcomeActivity()
        }, 3000L)
    }

    private fun goToWelcomeActivity() {
        Intent(this, welcome1::class.java).also {
            startActivity(it)
            finish()
        }
    }

}
