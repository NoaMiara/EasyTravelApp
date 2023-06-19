package com.app.easy.travel.repository

import androidx.lifecycle.MutableLiveData
import com.app.easy.travel.helpers.TRAVELS
import com.app.easy.travel.helpers.USERS
import com.app.easy.travel.model.Travel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeRepository {


    private var dataBase: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child(USERS)

    @Volatile
    private var instance: HomeRepository? = null

    fun getInstance(): HomeRepository? {
        return instance ?: synchronized(this) {

            val newInstance = HomeRepository()
            instance = newInstance
            instance
        }
    }

    fun loadTravel(
        travelMap: MutableLiveData<Travel>,
        flightMap: MutableLiveData<Double>,
        hotelMap: MutableLiveData<Double>,
        priceMap: MutableLiveData<Double>,
        userEmail: String, travelUri: String
    ) {
        dataBase.child(userEmail).child(TRAVELS).child(travelUri)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val myTravel: Travel? = snapshot.getValue(Travel::class.java)
                        travelMap.postValue(myTravel)
                        totalPrice(flightMap, hotelMap, priceMap, myTravel)

                    } catch (e: Exception) {
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun totalPrice(
        flightMap: MutableLiveData<Double>,
        hotelMap: MutableLiveData<Double>,
        priceMap: MutableLiveData<Double>,
        myTravel: Travel?
    ) {
        var flights = myTravel?.flights?.values
        var sumFlightTotalPrice = 0.0
        if (!flights.isNullOrEmpty()) {
            for (fly in flights) {
                sumFlightTotalPrice += fly.ticketPrice
            }
        }

        var hotels = myTravel?.hotels?.values
        var sumHotelTotalPrice = 0.0
        if (!hotels.isNullOrEmpty()) {
            for (hot in hotels) {
                sumHotelTotalPrice += hot.price
            }
        }
        flightMap.postValue(sumFlightTotalPrice)
        hotelMap.postValue(sumHotelTotalPrice)
        priceMap.postValue(sumFlightTotalPrice + sumHotelTotalPrice)


    }

    fun fetchPopularAttractions(
        locationName: String
    ) {

    }
}