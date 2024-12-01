package com.example.myapplication.data

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


fun SaveUserDataToFirestore(user: User, context: Context, callback: (Boolean) -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser

    currentUser?.reload()?.addOnCompleteListener { reloadTask ->
        if (reloadTask.isSuccessful) {
            val userMap = mapOf(
                "name" to user.name,
                "email" to user.email,
                "country" to user.country,
                "dateOfBirth" to user.dateOfBirth,
                "gender" to user.gender
            )
            Log.d("userData", user.toString())

            // Simpan data ke koleksi "users" dengan ID dokumen berdasarkan userId
            firestore.collection("users").document(user.id)
                .set(userMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("loginStatus", "Data saved for verified account")
                        callback(true)
                    } else {
                        Log.e("RegisterActivity", "Failed to save data: ${task.exception?.message}")
                        callback(false)
                    }
                }
        } else {
            Log.e("loginStatus", "Failed to reload user data: ${reloadTask.exception?.message}")
            callback(false)
        }
    }
}