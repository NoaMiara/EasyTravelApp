package com.app.easy.travel.add.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.easy.travel.repository.TaskRepository
import com.app.easy.travel.model.Task
import com.app.easy.travel.helpers.travel

class TaskViewModel : ViewModel() {
    private val repository: TaskRepository = TaskRepository()
    private val listOfTask = MutableLiveData<MutableList<Task>>()
    val publicAllTask: LiveData<MutableList<Task>> = listOfTask

    private var isView = false
    private var travelUriViewModel: String? = null
    private var userEmailViewModel: String? = null

    fun loadData(isModeAddMoreForTravel: Boolean, travelUri: String?, userEmail: String?) {

        if (!isModeAddMoreForTravel) {
            listOfTask.value = mutableListOf()
            if (!travel.tasks.values.isNullOrEmpty()) {
                val list: MutableList<Task> = mutableListOf()
                list.addAll(travel.tasks.values)
                listOfTask.postValue(list)
            }
        } else {
            isView = true
            travelUriViewModel = travelUri
            userEmailViewModel = userEmail
            repository.loadTasks(travelUriViewModel!!, listOfTask, userEmailViewModel!!)
        }
    }

    fun addTask(newTask: Task) {
        val list = if (listOfTask.value !=null) listOfTask.value else mutableListOf()
        list!!.add(newTask)
        if (isView) {
            repository.addTask(travelUriViewModel!!, newTask, userEmailViewModel!!)
        } else {
            travel.tasks.put(newTask.pid!!, newTask)
        }
        listOfTask.postValue(list!!)


    }

    fun remove(removeTask: Task) {
        listOfTask.value?.remove(removeTask)
        listOfTask.postValue(listOfTask.value)
        if (isView) {
            repository.remove(removeTask, travelUriViewModel!!, userEmailViewModel!!)
        } else {
            travel.tasks.remove(removeTask.pid)

        }
    }


    fun updateTask(pid: String, updateTask: String, completed: Boolean) {
        val list = listOfTask.value
        val task = list?.find { it.pid == pid }
        task?.task = updateTask
        task?.completed = completed
        listOfTask.postValue(list!!)

        if (isView) {
            repository.addTask(travelUriViewModel!!, task!!, userEmailViewModel!!)

        } else {
            travel.tasks.put(pid, Task(updateTask,pid, completed))

        }
    }

    fun setCompleted(pid: String) {
        val list = listOfTask.value
        val task = list?.find { it.pid == pid }
        if (task?.completed == true) {
            task.completed = false
        } else {
            task?.completed = true
        }
        if (isView) {
            repository.addTask(travelUriViewModel!!, task!!, userEmailViewModel!!)
        } else {
            travel.tasks.put(pid, Task( task!!.task,pid, task.completed))

        }
        listOfTask.postValue(list!!)
    }


}