/*
 * This code defines a HotelViewModel class that is responsible for managing hotel data.
 * It uses a HotelRepository to retrieve and manipulate hotel data, and provides two
 * functions for loading hotel data and removing hotels.
 *
 * The class is part of the com.app.easy.travel.view.hotel package and extends the ViewModel class
 * provided by the Android Architecture Components library. It also uses LiveData to observe changes
 * in the hotel data and update the UI accordingly.
 */

package com.app.easy.travel.ui.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.model.Hotel
import com.app.easy.travel.repository.HotelRepository

class HotelViewModel : ViewModel() {

    // Create an instance of the HotelRepository class
    private val repository: HotelRepository = HotelRepository()

    // Create a MutableLiveData object to hold the list of hotels
    private val allHotel = MutableLiveData<List<Hotel>>()

    // Create a LiveData object that can be observed by UI components to receive updates on the list of hotels
    val publicAllHotel: LiveData<List<Hotel>> = allHotel

    /*
     * This function is used to load hotel data from the repository based on the given travelUri and userEmail.
     * It calls the loadHotel function of the repository and passes it the MutableLiveData object allHotel and the
     * travelUri and userEmail parameters.
     */
    fun loadData(travelUri:String,userEmail:String){
        repository.loadHotel(travelUri,allHotel,userEmail)
    }

    /*
     * This function is used to remove a specific hotel based on its position, travelUri, and userEmail.
     * It retrieves the hotel at the specified position from the publicAllHotel LiveData object, and then passes it
     * to the remove function of the repository along with the travelUri and userEmail parameters.
     */
    fun remove(position: Int, travelUri: String,userEmail:String) {
        publicAllHotel.value?.get(position)?.let {
            repository.remove(it,travelUri,userEmail)
        }
    }
}
