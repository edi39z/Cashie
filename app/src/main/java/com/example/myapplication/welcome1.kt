//package com.example.myapplication
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//
//class welcome1 : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_welcome1)
//
//        // Navigasi ke welcome2 saat root layout diklik
//        findViewById<View>(R.id.main).setOnClickListener {
//            Intent(this, welcome2::class.java).also {
//                startActivity(it)
//                finish()
//            }
//        }
//    }
//}
