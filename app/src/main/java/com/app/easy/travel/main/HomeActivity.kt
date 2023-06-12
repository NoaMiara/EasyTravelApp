package com.app.easy.travel.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.app.easy.travel.R

import com.app.easy.travel.add.MainAddActivity
import com.app.easy.travel.databinding.ActivityHomeBinding
import com.app.easy.travel.helpers.*
import com.app.easy.travel.login.LoginActivity

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForLoginScreen()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Intent(this, MainAddActivity::class.java).apply {
                startActivity(this)
            }

        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
         navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        headerManagement()

    }

    private fun headerManagement() {

        val preferences = getSharedPreferences(USER,MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL,"")
        val firstName =preferences.getString(USER_FIRST_NAME,"")
        val lastName =preferences.getString(USER_LAST_NAME,"")

       var headerView = navView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.userName).text="Welcome $firstName $lastName"
    }




    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun checkForLoginScreen() {
        val preferences = getSharedPreferences(USER, MODE_PRIVATE)
        val firstTime = preferences.getBoolean(FIRST_TIME, true)

        if (firstTime) {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {

            R.id.nav_logout -> {
                val preferences = getSharedPreferences(USER, MODE_PRIVATE)
                preferences.edit().clear().commit()
                Intent(this, LoginActivity::class.java).apply {
                    startActivity(this)
                    finish()

                }
            }

        }
        return true
    }


}