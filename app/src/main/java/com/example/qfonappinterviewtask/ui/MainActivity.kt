package com.example.qfonappinterviewtask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.qfonappinterviewtask.R
import com.example.qfonappinterviewtask.databinding.ActivityMainBinding
import com.example.qfonappinterviewtask.ui.createpoll.CreatePollActivity
import com.example.qfonappinterviewtask.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val homeViewModel:HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigationAndBottomBar()
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.addPollButton.setOnClickListener {

            val currentDestinationId = navController.currentDestination?.id
            if (currentDestinationId == R.id.currentPollFragment) {
                homeViewModel.navigatedToNextScreenConfirm(true)
            }

            val intent = Intent(this,CreatePollActivity::class.java)
            startActivity(intent)


        }
    }



    private fun setUpNavigationAndBottomBar() {
        val bottomBar = binding.bottomNavView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        bottomBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.historyFragment -> {
                    homeViewModel.navigatedToNextScreenConfirm(true)
                }
                R.id.currentPollFragment -> {
                    homeViewModel.navigatedToNextScreenConfirm(false)
                }
            }
        }
    }

}