package com.app.easy.travel.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.repository.TravelRepository
import com.app.easy.travel.model.Travel

class HomeViewModel : ViewModel() {

    private val repository: TravelRepository = TravelRepository()
    private val allTravels = MutableLiveData<List<Travel>>()
    val publicAllTravels: LiveData<List<Travel>> = allTravels


    fun loadTravels(userEmail:String) {

        repository.loadTravels(allTravels,userEmail)
    }

    fun remove(position: Int,userEmail:String) {
        publicAllTravels.value?.get(position)?.let { repository.remove(it,userEmail) }

    }

    fun getTravel(position: Int): Travel? {
        return publicAllTravels.value?.get(position)

    }


}




