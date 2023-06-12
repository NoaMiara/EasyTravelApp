package com.app.easy.travel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.app.easy.travel.R
import com.app.easy.travel.databinding.ActivityViewMainBinding
import com.app.easy.travel.helpers.TRAVEL_URI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewMainBinding
    lateinit var travelUri: String

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        actionBarManager()
        super.onCreate(savedInstanceState)

        travelUri = intent.getStringExtra(TRAVEL_URI).toString()
        binding = ActivityViewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        controller()

    }

    private fun controller() {
        val navViewAdd: BottomNavigationView = binding.navViewTravel
        navController = findNavController(R.id.nav_host_fragment_activity_view_travel)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_flight,
                R.id.navigation_hotels,
                R.id.navigation_to_do_list,
                R.id.navigation_camera,
                //R.id.navigation_flight_detels_list
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navViewAdd.setupWithNavController(navController)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

    }

    private fun actionBarManager() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    @JvmName("getTravelUri1")
    fun getTravelUri(): String {
        return travelUri
    }


}