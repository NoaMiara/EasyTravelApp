package com.app.easy.travel.add

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.app.easy.travel.add.flight.AddFlightActivity
import com.app.easy.travel.add.hotel.AddHotelActivity
import com.app.easy.travel.add.tasks.AddTaskActivity
import com.app.easy.travel.databinding.ActivityAddBinding
import com.app.easy.travel.helpers.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainAddActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityAddBinding
    private lateinit var dataBase: DatabaseReference
    private lateinit var productImageRef: StorageReference
    private lateinit var userEmail: String
    private val calender = Calendar.getInstance()
    private val format = SimpleDateFormat("dd/MM/yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBarManager()
        preferencesManager()
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()


    }

    private fun setOnClick() {
        binding.btnAddFlight.setOnClickListener(this)
        binding.btnAddHotels.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
        binding.btnAddTodo.setOnClickListener(this)
        binding.date.setOnClickListener(this)

    }

    private fun preferencesManager() {
        val preferences = getSharedPreferences(USER, MODE_PRIVATE)
        val email = preferences.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }

    private fun actionBarManager() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Travel"
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.btnAddFlight -> {
                Intent(this, AddFlightActivity::class.java).apply {
                    startActivity(this)
                }
            }
            binding.date -> {
                DatePickerDialog(
                    this,
                    this,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }
            binding.btnAddHotels -> {
                Intent(this, AddHotelActivity::class.java).apply {
                    startActivity(this)
                }

            }
            binding.btnAddTodo -> {
                Intent(this, AddTaskActivity::class.java).apply {
                    startActivity(this)
                }
            }
            binding.btnSave -> {
                if (binding.etLocationName.text.isNullOrEmpty()) {
                    Toast.makeText(this, "Please write down the location name", Toast.LENGTH_LONG)
                        .show()
                } else {
                    saveOnFireBase()
                    saveOnStorageFireBase()
                    travel.clear()
                    imagesCurrentTravel.clear()
                    finish()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        travel.clear()
        flight.removeAll()
        hotel.removeAll()
        imageData.clear()
    }


    private fun saveOnStorageFireBase() {

        productImageRef = FirebaseStorage.getInstance().reference.child(IMAGES)
            .child(userEmail)
        for (image in imagesCurrentTravel) {
            val baos = ByteArrayOutputStream()
            val childPid = image.pid
            val mountainImagesRef = productImageRef.child(childPid)
            var k = image.image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = mountainImagesRef.putBytes(data)
            uploadTask.addOnFailureListener {
                Toast.makeText(this, "Failure image Storage", Toast.LENGTH_LONG).show()
            }.addOnSuccessListener {
                Toast.makeText(this, "Success image Storage", Toast.LENGTH_LONG).show()

            }
        }

    }

    private fun saveOnFireBase() {
        travel.pid = "${UUID.randomUUID()}"
        travel.name = binding.etLocationName.text.toString()
        travel.date = binding.date.text.toString()

        dataBase = FirebaseDatabase.getInstance().reference.child(USERS)
        dataBase.child(userEmail).child(TRAVELS).child(travel.pid!!)
            .setValue(travel).addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calender.set(year, month, dayOfMonth)
        displayFormattedDate(calender.timeInMillis)
    }

    private fun displayFormattedDate(time:Long){

        binding.date.text = format.format(time)

    }


}