package com.app.easy.travel.add.hotel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.easy.travel.R
import com.app.easy.travel.databinding.ActivityAddHotelBinding
import com.app.easy.travel.helpers.TRAVEL_URI
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddHotelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHotelBinding
    var isAddMoreForTravel: Boolean = false;
    var uriTravel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uriTravel = intent.getStringExtra(TRAVEL_URI)
        modeAddMoreForTravel()
        binding = ActivityAddHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        controller()


    }

    private fun controller() {
        val navViewAdd: BottomNavigationView = binding.navViewAddHotel
        val navController = findNavController(R.id.nav_host_fragment_activity_add_hotel)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_hotel_info,
                R.id.navigation_hotel_ticket,
                R.id.navigation_save_hotel,
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