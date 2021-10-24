package com.neflis.neflis.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.neflis.neflis.R
import com.neflis.neflis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

    }


    private fun initToolBar(){

        //val navController = findNavController(R.id.nav_host_fragment_content_homeº)

        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        //NavigationUI.setupWithNavController(binding.mainToolbar, navController, appBarConfiguration)
//
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//
//            when(destination.id){
//                R.id.publicProfileFragment -> {
//                    binding.deleteImg.visibility = View.VISIBLE
//                    binding.mainToolbar.title = getString(R.string.contactos)
//                    binding.mainToolbar.navigationIcon = null
//                }
//                else -> {
//                    binding.deleteImg.visibility = View.GONE
//                    binding.mainToolbar.title = getString(R.string.contactos)
//                    binding.mainToolbar.navigationIcon = null
//                }
//            }
//        }
    }

}