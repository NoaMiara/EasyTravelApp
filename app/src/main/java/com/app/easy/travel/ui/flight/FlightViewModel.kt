package com.app.easy.travel.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.model.Flight
import com.app.easy.travel.repository.FlightRepository

class FlightViewModel : ViewModel() {

    // Create an instance of the repository to interact with the data access layer
    private val repository: FlightRepository = FlightRepository()

    // MutableLiveData to hold a list of all flights, updated by the repository
    private val allFlight = MutableLiveData<List<Flight>>()

    // Expose a LiveData version of the list of all flights to the UI to observe
    val publicAllFlight: LiveData<List<Flight>> = allFlight

    // Function to load flight data for a specific travel from the repository
    fun loadData(TravelUri:String,userEmail:String){
        repository.loadFlights(TravelUri,allFlight,userEmail)
    }

    // Function to remove a flight at a specific position from the repository
    fun remove(position: Int, travelUri: String,userEmail:String) {

        // Get the flight object at the specified position from the MutableLiveData object
        publicAllFlight.value?.get(position)?.let {
            // Call the corresponding function in the repository with the provided parameters
            repository.remove(it,travelUri,userEmail)
        }

    }

}
