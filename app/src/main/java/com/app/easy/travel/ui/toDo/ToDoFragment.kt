package com.app.easy.travel.ui.toDo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.adapter.TaskRecyclerViewAdapter
import com.app.easy.travel.add.tasks.NewTaskDialog
import com.app.easy.travel.add.tasks.TaskViewModel
import com.app.easy.travel.databinding.FragmentToDoBinding
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.intarface.TaskItemClick
import com.app.easy.travel.model.Task
import com.app.easy.travel.ui.MainViewActivity

class ToDoFragment : Fragment(), TaskItemClick {

    // Declare variables
    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerview: RecyclerView
    private lateinit var userEmail: String
    private lateinit var travelUri: String

    // Initialize view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the travel URI
        travelUri = (activity as MainViewActivity?)?.getTravelUri().toString()
        // Initialize the task view model
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        // Inflate the layout for this fragment
        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Set up the view after it has been created
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        // Get the user email from shared preferences
        preferencesManager()
        // Load the data into the task view model
        taskViewModel.loadData(true, travelUri, userEmail)

        // Get the recycler view
        recyclerview = binding.rvTask
        // Set up the recycler view
        setRecyclerView()

        // Set up the add task button
        binding.btnAddTask.setOnClickListener {
            // Show the new task dialog
            NewTaskDialog(null,false).show(childFragmentManager, "newTaskTag")
        }
    }

    // Get the user email from shared preferences
    private fun preferencesManager() {
        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }

    // Load user preferences and set the RecyclerView adapter with the list of tasks
    private fun setRecyclerView() {
        // Observe the public task data in the task view model
        taskViewModel.publicAllTask.observe(viewLifecycleOwner, Observer {
            // Set up the recycler view with the task data
            recyclerview.apply {
                layoutManager = LinearLayoutManager(activity)
                recyclerview.adapter = TaskRecyclerViewAdapter(this@ToDoFragment, it)
            }
        })
    }

    // Set up the alert dialog for task deletion
    private fun alertDialogForImageDelete(task: Task, position: Int) {
        val pictureDialog = AlertDialog.Builder(requireActivity())

        pictureDialog.setTitle("Would you like to remove the task")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->
                taskViewModel.remove(task)
            }.show()
    }

    // Clean up the binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Open the edit task dialog
    override fun editTask(task: Task) {
        NewTaskDialog(task,false).show(childFragmentManager, "editTaskTag")
    }

    // Mark the task as completed
    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task.pid!!)
    }

    // Show an alert dialog for deleting a task
    override fun removeTask(task: Task, position: Int) {
        alertDialogForImageDelete(task, position)
    }

}