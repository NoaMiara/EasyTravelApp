package com.app.easy.travel.ui.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.model.Image
import com.app.easy.travel.repository.FileFlightRepository

class FileFlightViewModel :ViewModel() {
    private val repository: FileFlightRepository = FileFlightRepository()
    private val allFile = MutableLiveData<List<Image>>()
    val publicAllFile: LiveData<List<Image>> = allFile


    fun loadData(travelUri: String, flightUri: String, userEmail: String){
        repository.loadFile(travelUri,flightUri,allFile,userEmail)
    }

    fun remove(position: Int,travelUri:String,flightUri:String,userEmail: String) {
        publicAllFile.value?.get(position)?.let {
            repository.remove(it.pid,travelUri,flightUri,userEmail)
        }

    }


}