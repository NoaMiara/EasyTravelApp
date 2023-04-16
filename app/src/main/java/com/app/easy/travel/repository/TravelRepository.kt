package com.app.easy.travel.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easy.travel.helpers.IMAGES
import com.app.easy.travel.helpers.TRAVELS
import com.app.easy.travel.helpers.USERS
import com.app.easy.travel.model.Flight
import com.app.easy.travel.model.Hotel
import com.app.easy.travel.model.Travel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class TravelRepository {

    private var dataBase: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child(USERS)
    private var productImageRef: StorageReference=
        FirebaseStorage.getInstance().reference.child(IMAGES)

    @Volatile
    private var instance: TravelRepository? = null

    fun getInstance(): TravelRepository? {
        return instance ?: synchronized(this) {


            val newInstance = TravelRepository()
            instance = newInstance
            instance
        }
    }

    fun loadTravels(travelMap: MutableLiveData<List<Travel>>, userEmail:String) {
        dataBase.child(userEmail).child(TRAVELS)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val travelList: List<Travel> = snapshot.children.map { data ->
                        data.getValue(Travel::class.java)!!
                    }
                    Log.d("gtes",travelList.toString())
                    travelMap.postValue(travelList)
                } catch (e: Exception) {

                }


            }

            override fun onCancelled(error: DatabaseError) {
//                Log.d("firebase", "Got value ${post}")
            }

        })
    }

    fun remove(travelToRemove: Travel, userEmail:String) {
        var flights =travelToRemove.flights.values.toMutableList();
        var hotels =travelToRemove.hotels.values.toMutableList();
        removeHotelFromStorage(hotels,userEmail)
        removeFlightFromStorage(flights,userEmail)
        var removeTravel = travelToRemove.pid?.let { dataBase.child(userEmail).child(TRAVELS).child(it) }
        removeTravel?.removeValue()
            ?.addOnSuccessListener {
                Log.d("removeFromFirebase", "Success to remove ")

            }?.addOnFailureListener {
                Log.d("removeFromFirebase", "Failure to remove ")

            }

    }
    private fun removeHotelFromStorage(hotels: MutableList<Hotel>, userEmail:String) {
        for (hotel in hotels) {
            val files = hotel.file
            if (files != null) {
                for (file in files) {
                    productImageRef.child(userEmail).child(file.key).delete().addOnSuccessListener {
                        Log.d("removeHotelFromStorage", "Success to remove hotel image ")

                    }.addOnFailureListener {
                        Log.d("removeHotelFromStorage", "Failure to remove hotel image ")

                    }

                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun removeFlightFromStorage(flights: MutableList<Flight>, userEmail:String) {
        for (flight in flights) {
            val files = flight.file
                if (files != null) {
                    for (file in files) {
                        productImageRef.child(userEmail).child(file.key).delete().addOnSuccessListener {
                            Log.d("removeFlightFromStorage", "Success to remove flight image ")

                        }.addOnFailureListener {
                            Log.d("removeFlightFromStorage", "Failure to remove flight image ")

                        }

                }
            }
        }
    }

}