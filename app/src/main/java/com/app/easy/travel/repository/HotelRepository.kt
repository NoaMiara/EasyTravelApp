package com.app.easy.travel.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easy.travel.helpers.HOTELS
import com.app.easy.travel.helpers.IMAGES
import com.app.easy.travel.helpers.TRAVELS
import com.app.easy.travel.helpers.USERS
import com.app.easy.travel.model.Hotel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class HotelRepository {

    private var dataBase: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child(USERS)

    private var flightImageRef: StorageReference =
        FirebaseStorage.getInstance().reference.child(IMAGES)


    @Volatile
    private var instance: HotelRepository? = null
    fun getInstance(): HotelRepository? {
        return instance ?: synchronized(this) {
            val newInstance = HotelRepository()
            instance = newInstance
            instance
        }
    }

    fun loadHotel(travelUri: String, hotelMap: MutableLiveData<List<Hotel>>, userEmail:String) {
        dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(HOTELS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {

                        val hotelList: List<Hotel> = snapshot.children.map { data ->
                            data.getValue(Hotel::class.java)!!
                        }
                        hotelMap.postValue(hotelList)

                    } catch (e: Exception) {
                        Log.d("loadFlights", "Got value catch ")


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }


    fun remove(hotelToRemove: Hotel, travelUri: String, userEmail:String) {

        removeFromStorage(hotelToRemove,userEmail)

        val removeHotel = hotelToRemove.pid?.let {
            dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(HOTELS).child(it)
        }
        removeHotel?.removeValue()
            ?.addOnSuccessListener {
                Log.d("removeHotel", "Success to remove ")

            }?.addOnFailureListener {
                Log.d("removeHotel", "Failure to remove ")

            }

    }


    private fun removeFromStorage(hotels: Hotel, userEmail: String) {
        val files = hotels.file?.values
        if (files != null) {
            for (file in files) {
                flightImageRef.child(userEmail).child(file).delete().addOnSuccessListener {
                    Log.d("removeFromStorage", "Success to remove flight image ")

                }.addOnFailureListener {
                    Log.d("removeFromStorage", "Failure to remove flight image ")

                }

            }
        }
    }



}