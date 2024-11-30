package com.example.myapplication.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
<<<<<<< HEAD:app/src/main/java/com/example/myapplication/Views/LoadingActivity.kt
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.welcome1

@SuppressLint("Loading")
=======
@SuppressLint("CustomSplashScreen")
>>>>>>> 595b14847176281e0af82b234d4d92c47919c381:app/src/main/java/com/example/myapplication/LoadingActivity.kt
class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

<<<<<<< HEAD:app/src/main/java/com/example/myapplication/Views/LoadingActivity.kt
        Handler(Looper.getMainLooper()).postDelayed({
            goToWelcomeActivity()
        }, 3000L)
    }

    private fun goToWelcomeActivity() {
        Intent(this, welcome1::class.java).also {
            startActivity(it)
            finish()
        }
=======
        // Pindah ke OnboardingActivity setelah 3 detik
        Handler().postDelayed({
            val intent = Intent(this, activity_onboarding::class.java)
            startActivity(intent)
            finish() // Menutup LoadingActivity agar tidak bisa kembali ke halaman ini
        }, 3000) // 3000ms = 3 detik
>>>>>>> 595b14847176281e0af82b234d4d92c47919c381:app/src/main/java/com/example/myapplication/LoadingActivity.kt
    }

}
