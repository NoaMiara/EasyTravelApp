package com.app.easy.travel.view.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.repository.FlightRepository
import com.app.easy.travel.model.Flight

class FlightViewModel : ViewModel() {

    private val repository: FlightRepository = FlightRepository()
    private val allFlight = MutableLiveData<List<Flight>>()
    val publicAllFlight: LiveData<List<Flight>> = allFlight

    fun loadData(TravelUri:String,userEmail:String){
        repository.loadFlights(TravelUri,allFlight,userEmail)
    }

    fun remove(position: Int, travelUri: String,userEmail:String) {
        publicAllFlight.value?.get(position)?.let {
            repository.remove(it,travelUri,userEmail)
        }

    }



}