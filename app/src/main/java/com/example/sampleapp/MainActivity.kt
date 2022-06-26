package com.example.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.data.local.DataStorePrefsManager
import com.example.sampleapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceStorage: DataStorePrefsManager

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.findNavController()
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        navController.graph = navGraph
    }

//    override fun onBackPressed() {
//        val currentDestination = navController.currentDestination
//        if (currentDestination?.id == R.id.homeFragment || currentDestination?.id == R.id.loginFragment) {
//            finish()
//        } else {
//            navController.popBackStack()
//        }
//    }
}