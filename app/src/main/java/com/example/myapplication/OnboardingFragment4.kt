package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class OnboardingFragment4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.welcome4, container, false)

        // Menemukan tombol berdasarkan ID
        val btnSignIn: TextView = view.findViewById(R.id.btnSignIn)

        // Set listener untuk menangani klik tombol
        btnSignIn.setOnClickListener {
            // Pindahkan ke SignInActivity
            val intent = Intent(requireActivity(), SignInActivity::class.java) // gunakan requireActivity() untuk context
            startActivity(intent)
            // Tidak perlu menutup activity karena ini akan beralih ke aktivitas lain
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): OnboardingFragment4 {
            return OnboardingFragment4()
        }
    }
}
