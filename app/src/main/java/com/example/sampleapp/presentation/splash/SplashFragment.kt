package com.example.sampleapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.MainActivity
import com.example.sampleapp.R
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var isLoggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        lifecycleScope.launch {
            isLoggedIn = (activity as SplashActivity).preferenceStorage.getProfileStatusFromDataStore()
            startCounter()
        }

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun startCounter() {
        Handler(Looper.getMainLooper()).postDelayed({

            if (isLoggedIn){
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("isLoggedIn", isLoggedIn)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                (activity as SplashActivity).finish()
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }


        }, 1200)
    }
}