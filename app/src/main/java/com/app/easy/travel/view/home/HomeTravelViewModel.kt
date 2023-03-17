package com.app.easy.travel.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.repository.HomeRepository
import com.app.easy.travel.model.Travel

class HomeTravelViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepository()
    private val myTravel = MutableLiveData<Travel>()
    val myTravelOb: LiveData<Travel> = myTravel
    private val flightPrice =MutableLiveData<Double>()
    val flightTotalPrice :LiveData<Double> =flightPrice
    private val hotelPrice =MutableLiveData<Double>()
    val hotelTotalPrice :LiveData<Double> =hotelPrice
    private val price =MutableLiveData<Double>()
    val totalPrice :LiveData<Double> =price

    fun loadTravel(userEmail:String,travelUri:String) {
        repository.loadTravel(myTravel,flightPrice,hotelPrice,price,userEmail,travelUri)
    }

}