package com.app.easy.travel.add.flight

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.easy.travel.R
import com.app.easy.travel.databinding.ActivityAddFlightBinding
import com.app.easy.travel.helpers.TRAVEL_URI

class AddFlightActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFlightBinding
    var isAddMoreForTravel: Boolean = false;
    var uriTravel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uriTravel = intent.getStringExtra(TRAVEL_URI)
        modeAddMoreForTravel()
        binding = ActivityAddFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller()
    }

    private fun controller() {
        val navViewAdd: BottomNavigationView = binding.navViewAddFlight
        val navController = findNavController(R.id.nav_host_fragment_activity_add_flight)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_arrival,
                R.id.navigation_departure,
                R.id.navigation_flight_ticket,
                R.id.navigation_save_flight
            )
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navViewAdd.setupWithNavController(navController)

    }

    private fun modeAddMoreForTravel() {
        if (!uriTravel.isNullOrEmpty()) {
            isAddMoreForTravel = true
        }
    }


}


