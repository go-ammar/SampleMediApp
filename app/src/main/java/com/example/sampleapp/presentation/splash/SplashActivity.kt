package com.example.sampleapp.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.MainActivity
import com.example.sampleapp.R
import com.example.sampleapp.data.local.DataStorePrefsManager
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceStorage: DataStorePrefsManager

    lateinit var binding: ActivitySplashBinding
    private var isLoggedIn = false
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val fromHome = intent.getBooleanExtra("fromHome", false)

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.findNavController()
        val navGraph = navController.navInflater.inflate(R.navigation.login_graph)


        if (fromHome)
            navGraph.startDestination = R.id.loginFragment
        else
            navGraph.startDestination = R.id.splashFragment

        navController.graph = navGraph

    }

    override fun onBackPressed() {
        finish()
    }
}