package com.app.easy.travel.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easy.travel.helpers.TASKS
import com.app.easy.travel.helpers.TRAVELS
import com.app.easy.travel.helpers.USERS
import com.app.easy.travel.model.Task
import com.google.firebase.database.*
import java.lang.Exception

class TaskRepository {



    private var dataBase: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child(USERS)


    @Volatile
    private var instance: HotelRepository? = null
    fun getInstance(): HotelRepository? {
        return instance ?: synchronized(this) {
            val newInstance = HotelRepository()
            instance = newInstance
            instance
        }
    }

    fun loadTasks(travelUri: String, taskMap: MutableLiveData<MutableList<Task>>, userEmail:String) {
        dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(TASKS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {

                        val taskList: MutableList<Task> = snapshot.children.map { data ->
                            data.getValue(Task::class.java)!!
                        } as MutableList<Task>
                        Log.d("taskList", taskList.toString())

                        taskMap.postValue(taskList)
                        Log.d("taskList", taskMap.value.toString())

                    } catch (e: Exception) {
                        Log.d("loadTasks", "Got value catch ")


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun addTask(travelUri: String, task: Task, userEmail:String){
        dataBase.child(userEmail).child(TRAVELS).child(travelUri)
            .child(TASKS).child(task.pid!!)
            .setValue(task).addOnSuccessListener {

                Log.d("addTaskinn", "Success")

            }.addOnFailureListener {
                Log.d("addTask", "Failure")

            }
    }


    fun remove(TaskToRemove: Task, travelUri: String, userEmail:String) {


        val removeHotel = TaskToRemove.pid?.let {
            dataBase.child(userEmail).child(TRAVELS).child(travelUri).child(TASKS).child(it)
        }
        removeHotel?.removeValue()
            ?.addOnSuccessListener {
                Log.d("remove", "Success to remove ")

            }?.addOnFailureListener {
                Log.d("remove", "Failure to remove ")

            }

    }



}