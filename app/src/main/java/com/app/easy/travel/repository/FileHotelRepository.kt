package com.app.easy.travel.repository

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easy.travel.helpers.*
import com.app.easy.travel.model.Image
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.lang.Exception

class FileHotelRepository {

    private var dataBase: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child(USERS)

    private var fileImageRef: StorageReference =
        FirebaseStorage.getInstance().reference.child(IMAGES)

    @Volatile
    private var instance: FileHotelRepository? = null
    fun getInstance(): FileHotelRepository? {
        return instance ?: synchronized(this) {
            val newInstance = FileHotelRepository()
            instance = newInstance
            instance
        }
    }

    fun loadFile(
        travelUri: String,
        hotelUri: String,
        hotelMap: MutableLiveData<List<Image>>,
        userEmail: String
    ) {
        dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(HOTELS).child(hotelUri)
            .child(FILE).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val fileList: List<String> = snapshot.children.map { data ->
                            data.getValue(String::class.java)!!
                        }
                        loadFromStorage(fileList, hotelUri, hotelMap, userEmail)
                    } catch (e: Exception) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {
//                Log.d("firebase", "Got value ${post}")
                }

            })
    }


    private fun loadFromStorage(
        fileList: List<String>,
        hotelUri: String,
        hotelMap: MutableLiveData<List<Image>>,
        userEmail: String
    ) {
        val list = mutableListOf<Image>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                for (file in fileList) {
                    val localFile = File.createTempFile(file, "$file.jpg")
                    fileImageRef.child(userEmail).child(file).getFile(localFile).await()
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    list.add(Image(file, bitmap))
                }
                hotelMap.postValue(list)
            } catch (e: Exception) {}
        }
    }


    fun remove(fileToRemove: String, travelUri: String, hotelUri: String, userEmail: String) {
        removeFromStorage(fileToRemove, userEmail)

        val removeFile =
            dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(HOTELS).child(hotelUri)
                .child(FILE).child(fileToRemove)
        removeFile?.removeValue()
            ?.addOnSuccessListener {
                Log.d("remove", "Success to remove ")

            }?.addOnFailureListener {
                Log.d("remove", "Failure to remove ")

            }

    }

    private fun removeFromStorage(fileToRemove: String, userEmail: String) {

        fileImageRef.child(userEmail).child(fileToRemove).delete().addOnSuccessListener {
            Log.d("removeFromStorage", "Success to remove hotel image ")

        }.addOnFailureListener {
            Log.d("removeFromStorage", "Failure to remove hotel image ")

        }

    }


}