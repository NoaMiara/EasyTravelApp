package com.app.easy.travel.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easy.travel.helpers.FLIGHTS
import com.app.easy.travel.helpers.IMAGES
import com.app.easy.travel.helpers.TRAVELS
import com.app.easy.travel.helpers.USERS
import com.app.easy.travel.model.Flight
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class FlightRepository() {

    private var dataBase: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child(USERS)

    private var flightImageRef: StorageReference =
        FirebaseStorage.getInstance().reference.child(IMAGES)

    @Volatile
    private var instance: FlightRepository? = null
    fun getInstance(): FlightRepository? {
        return instance ?: synchronized(this) {
            val newInstance = FlightRepository()
            instance = newInstance
            instance
        }
    }

    fun loadFlights(travelUri: String, flightMap: MutableLiveData<List<Flight>>, userEmail:String) {
        dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(FLIGHTS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        Log.d("firebase1111", "Got value in try")

                        val flightList: List<Flight> = snapshot.children.map { data ->
                            data.getValue(Flight::class.java)!!
                        }
                        flightMap.postValue(flightList)
                    } catch (e: Exception) {
                        Log.d("firebase1111", "Got value catch ")


                    }
                }

                override fun onCancelled(error: DatabaseError) {
//                Log.d("firebase", "Got value ${post}")
                }

            })
    }

    fun remove(flightToRemove: Flight, travelUri: String, userEmail:String) {

        removeFromStorage(flightToRemove,userEmail)

        val removeFlight = flightToRemove.pid?.let {
            dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(FLIGHTS).child(it)
        }
        removeFlight?.removeValue()
            ?.addOnSuccessListener {
                Log.d("firebase", "Success to remove ")

            }?.addOnFailureListener {
                Log.d("firebase", "Failure to remove ")

            }

    }

    private fun removeFromStorage(flights: Flight, userEmail: String) {
        val files = flights.file?.values
        if (files != null) {
            for (file in files) {
                flightImageRef.child(userEmail).child(file).delete().addOnSuccessListener {
                    Log.d("firebase", "Success to remove flight image ")

                }.addOnFailureListener {
                    Log.d("firebase", "Failure to remove flight image ")

                }

            }
        }
    }


}