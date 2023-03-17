package com.app.easy.travel.view.flight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.adapter.FileRecyclerViewAdapter
import com.app.easy.travel.databinding.ActivityFileFlightBinding
import com.app.easy.travel.helpers.FLIGHT_URI
import com.app.easy.travel.helpers.TRAVEL_URI
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.intarface.RecyclerViewInterface

class FileFlightActivity : AppCompatActivity(), RecyclerViewInterface {

    private lateinit var binding: ActivityFileFlightBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var filetViewModel: FileFlightViewModel
    private lateinit var fileRecyclerViewAdapter: FileRecyclerViewAdapter

    private lateinit var flightUri: String
    private lateinit var travelUri: String
    private lateinit var userEmail: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarManager()
        preferencesManager()
        filetViewModel = ViewModelProvider(this)[FileFlightViewModel::class.java]

        flightUri = intent.getStringExtra(FLIGHT_URI).toString()
        travelUri = intent.getStringExtra(TRAVEL_URI).toString()

        recyclerview = binding.recyclerviewFileFlight
        recyclerviewManager()

    }
    private fun recyclerviewManager(){
        recyclerview.apply {
            filetViewModel.loadData(travelUri!!, flightUri!!, userEmail)
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(context)
            recyclerview.setHasFixedSize(true)
            fileRecyclerViewAdapter = FileRecyclerViewAdapter(this@FileFlightActivity)
            // set the custom adapter to the RecyclerView
            recyclerview.adapter = fileRecyclerViewAdapter
            filetViewModel.publicAllFile.observe(this@FileFlightActivity) {
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
                filetViewModel.remove(position, travelUri, flightUri, userEmail)
                fileRecyclerViewAdapter.removeFile(position)

            }.show()
    }


    override fun onItemClick(delete: Boolean, position: Int) {
        alertDialogForImageDelete(position)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}