package com.app.easy.travel.repository

import android.graphics.Bitmap
import android.util.Log
import com.app.easy.travel.helpers.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class AddHotelRepository(private var travelUri: String, private var id: String,private var userEmail: String) {

    private lateinit var dataBase: DatabaseReference
    private lateinit var addHotelImageRef: StorageReference



    fun saveOnStorageFireBase() {
        addHotelImageRef = FirebaseStorage.getInstance().reference.child(IMAGES).child(userEmail)
        for (image in imageData) {
            val baos = ByteArrayOutputStream()
            val childPid = image.pid
            val mountainImagesRef = addHotelImageRef.child(childPid)
            image.image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = mountainImagesRef.putBytes(data)
            uploadTask.addOnFailureListener {
                Log.d("saveOnStorageFireBaseHotel", "Failure")

            }.addOnSuccessListener {
                Log.d("saveOnStorageFireBaseHotel", "Success")


            }
        }

    }

    fun saveOnFireBase() {
        dataBase = FirebaseDatabase.getInstance().reference.child(USERS)
        dataBase.child(userEmail).child(TRAVELS).child(travelUri)
            .child(HOTELS).child(id)
            .setValue(hotel).addOnSuccessListener {
                Log.d("saveOnFireBase", "Success")

            }.addOnFailureListener {
                Log.d("saveOnFireBase", "Failure")

            }

    }






}