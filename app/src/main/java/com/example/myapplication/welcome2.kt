//package com.example.myapplication
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//
//class welcome2 : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_welcome2)
//
//        // Navigasi ke welcome3 saat root layout diklik
//        findViewById<View>(R.id.main).setOnClickListener {
//            Intent(this, welcome3::class.java).also {
//                startActivity(it)
//                finish()
//            }
//        }
//    }
//}
