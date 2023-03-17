package com.app.easy.travel.view.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.repository.FileHotelRepository
import com.app.easy.travel.model.Image

class FileHotelViewModel: ViewModel() {
    private val repository: FileHotelRepository = FileHotelRepository()
    private val allFile = MutableLiveData<List<Image>>()
     val publicAllFile: LiveData<List<Image>> = allFile

    fun loadData(travelUri: String, hotelUri: String, userEmail: String){
        repository.loadFile(travelUri,hotelUri,allFile,userEmail)
    }

    fun remove(position: Int,travelUri:String,hotelUri:String,userEmail: String) {
        publicAllFile.value?.get(position)?.let {
            repository.remove(it.pid,travelUri,hotelUri,userEmail)
        }

    }


}