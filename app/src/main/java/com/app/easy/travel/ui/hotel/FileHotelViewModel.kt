package com.app.easy.travel.ui.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.model.Image
import com.app.easy.travel.repository.FileHotelRepository

class FileHotelViewModel: ViewModel() {

    // Create an instance of the repository to interact with the data access layer
    private val repository: FileHotelRepository = FileHotelRepository()

    // MutableLiveData to hold the list of hotel images, updated by the repository
    private val allFile = MutableLiveData<List<Image>>()

    // Expose a LiveData version of the list to the UI to observe
    val publicAllFile: LiveData<List<Image>> = allFile

    // Function to load the list of images from the repository
    fun loadData(travelUri: String, hotelUri: String, userEmail: String){

        // Call the corresponding function in the repository with the provided parameters
        repository.loadFile(travelUri,hotelUri,allFile,userEmail)
    }

    // Function to remove an image from the list
    fun remove(position: Int, travelUri:String, hotelUri:String, userEmail: String) {

        // Check if the image at the given position exists in the list
        publicAllFile.value?.get(position)?.let {

            // If it does, call the corresponding function in the repository to remove it
            repository.remove(it.pid,travelUri,hotelUri,userEmail)
        }
    }
}
