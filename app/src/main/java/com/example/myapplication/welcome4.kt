//package com.example.myapplication
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import com.example.myapplication.Views.SignInActivity  // Import SignInActivity dari Views package
//
//class welcome4 : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_welcome4)
//
//        // Navigasi ke SignInActivity saat root layout diklik
//        findViewById<View>(R.id.main).setOnClickListener {
//            // Intent menuju SignInActivity yang ada di Views
//            Intent(this, SignInActivity::class.java).also {
//                startActivity(it)
//                finish()
//            }
//        }
//    }
//}
