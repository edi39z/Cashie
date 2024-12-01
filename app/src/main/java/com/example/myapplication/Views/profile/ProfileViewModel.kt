package com.example.myapplication.views.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    // StateFlow untuk mengelola data user
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        // Memuat data pengguna saat ViewModel diinisialisasi
        fetchUserData()
    }

    /**
     * Mendapatkan data pengguna dari Firestore
     */
    private fun fetchUserData() {
        val firestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId.isNullOrEmpty()) {
            Log.e("ProfileViewModel", "User ID is null or empty")
            return
        }

        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fetchedUser = document.toObject(User::class.java)
                    if (fetchedUser != null) {
                        _user.value = fetchedUser
                    } else {
                        Log.e("ProfileViewModel", "Failed to parse user data")
                    }
                } else {
                    Log.e("ProfileViewModel", "No user document found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Error fetching user data: ${exception.message}")
            }
    }


    fun updateUserProfile(user: User, onComplete: (Boolean) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId.isNullOrEmpty()) {
            Log.e("ProfileViewModel", "User ID is null or empty")
            onComplete(false)
            return
        }

        firestore.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                _user.value = user // Perbarui StateFlow dengan data terbaru
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileViewModel", "Error updating user profile: ${exception.message}")
                onComplete(false)
            }
    }
}
