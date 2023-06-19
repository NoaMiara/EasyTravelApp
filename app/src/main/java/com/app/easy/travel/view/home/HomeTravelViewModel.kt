package com.app.easy.travel.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.easy.travel.model.Place
import com.app.easy.travel.model.Travel
import com.app.easy.travel.network.getPopularAttractions
import com.app.easy.travel.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeTravelViewModel : ViewModel() {

    // Create an instance of the repository to interact with the data access layer
    private val repository: HomeRepository = HomeRepository()

    // MutableLiveData to hold the selected travel object, updated by the repository
    private val myTravel = MutableLiveData<Travel>()

    // Expose a LiveData version of the selected travel object to the UI to observe
    val myTravelOb: LiveData<Travel> = myTravel

    // MutableLiveData to hold the total flight price, updated by the repository
    private val flightPrice = MutableLiveData<Double>()

    // Expose a LiveData version of the total flight price to the UI to observe
    val flightTotalPrice: LiveData<Double> = flightPrice

    // MutableLiveData to hold the total hotel price, updated by the repository
    private val hotelPrice = MutableLiveData<Double>()

    // Expose a LiveData version of the total hotel price to the UI to observe
    val hotelTotalPrice: LiveData<Double> = hotelPrice

    // MutableLiveData to hold the total price of the travel (flight + hotel), updated by the repository
    private val price = MutableLiveData<Double>()

    // Expose a LiveData version of the total price
    //of the travel to the UI to observe
    val totalPrice: LiveData<Double> = price


    private val _attractions = MutableLiveData<List<Place>?>()
    val attractions: MutableLiveData<List<Place>?> get() = _attractions

    // Function to load the selected travel data from the repository
    fun loadTravel(userEmail: String, travelUri: String) {
        // Call the corresponding function in the repository with the provided parameters
        repository.loadTravel(myTravel, flightPrice, hotelPrice, price, userEmail, travelUri)
    }

    fun getAttractions(destination: String) {
        Log.d("Places", "Getting attractions...")
        viewModelScope.launch {
            _attractions.value = getPopularAttractions(query = destination)
            Log.d("Places", "_attractions.value ==>> ${_attractions.value}")

        }
    }
}
