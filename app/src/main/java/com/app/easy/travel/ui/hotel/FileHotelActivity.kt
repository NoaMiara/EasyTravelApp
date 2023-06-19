package com.app.easy.travel.ui.hotel

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.adapter.FileRecyclerViewAdapter
import com.app.easy.travel.databinding.ActivityFileHotelBinding
import com.app.easy.travel.helpers.HOTEL_URI
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.intarface.RecyclerViewInterface

class FileHotelActivity : AppCompatActivity(), RecyclerViewInterface {


    private lateinit var binding: ActivityFileHotelBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var filetViewModel: FileHotelViewModel
    private lateinit var fileRecyclerViewAdapter: FileRecyclerViewAdapter

    private lateinit var hotelUri :String
    private lateinit var travelUri :String
    private lateinit var userEmail :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarManager()
        preferencesManager()
        filetViewModel = ViewModelProvider(this)[FileHotelViewModel::class.java]

        hotelUri = intent.getStringExtra(HOTEL_URI).toString()
        travelUri = intent.getStringExtra(TRAVEL_URI).toString()

        recyclerview = binding.recyclerviewFileHotel
        recyclerviewManager()

    }

    private fun recyclerviewManager() {
        recyclerview.apply {
            filetViewModel.loadData(travelUri!!,hotelUri!!,userEmail)
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(context)
            recyclerview.setHasFixedSize(true)
            fileRecyclerViewAdapter = FileRecyclerViewAdapter(this@FileHotelActivity)
            // set the custom adapter to the RecyclerView
            recyclerview.adapter = fileRecyclerViewAdapter


            filetViewModel.publicAllFile.observe(this@FileHotelActivity) {
                fileRecyclerViewAdapter.updateFile(it)
            }

        }
    }

    private fun actionBarManager() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "File"

    }
    private fun preferencesManager() {
        val preferences = getSharedPreferences(USER, MODE_PRIVATE)
        val email = preferences.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }

        private fun alertDialogForImageDelete(position: Int) {
        val pictureDialog = AlertDialog.Builder(this)

        pictureDialog.setTitle("Would you like to remove this file?")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->
                filetViewModel.remove(position,travelUri,hotelUri,userEmail)
                fileRecyclerViewAdapter.removeFile(position)


            }.show()
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onItemClick(delete: Boolean, position: Int) {
        alertDialogForImageDelete(position)
    }

}