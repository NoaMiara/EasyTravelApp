package com.app.easy.travel.view.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.repository.HotelRepository
import com.app.easy.travel.model.Hotel

class HotelViewModel : ViewModel() {
    private val repository: HotelRepository = HotelRepository()
    private val allHotel = MutableLiveData<List<Hotel>>()
    val publicAllHotel: LiveData<List<Hotel>> = allHotel

    fun loadData(travelUri:String,userEmail:String){
        repository.loadHotel(travelUri,allHotel,userEmail)

    }

    fun remove(position: Int, travelUri: String,userEmail:String) {
        publicAllHotel.value?.get(position)?.let {
            repository.remove(it,travelUri,userEmail)
        }

    }


}