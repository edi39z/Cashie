package com.example.myapplication.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

@SuppressLint("Loading")
class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Handler(Looper.getMainLooper()).postDelayed({
            goToSignInActivity()
        }, 3000L)
    }

    private fun goToSignInActivity() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
