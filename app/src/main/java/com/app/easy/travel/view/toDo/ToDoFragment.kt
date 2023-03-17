package com.app.easy.travel.view.toDo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.intarface.TaskItemClick
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.adapter.TaskRecyclerViewAdapter
import com.app.easy.travel.add.tasks.NewTaskDialog
import com.app.easy.travel.add.tasks.TaskViewModel
import com.app.easy.travel.databinding.FragmentToDoBinding
import com.app.easy.travel.model.Task
import com.app.easy.travel.view.MainViewActivity

class ToDoFragment : Fragment(), TaskItemClick {

    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerview: RecyclerView
    private lateinit var userEmail: String
    private lateinit var travelUri: String



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        travelUri = (activity as MainViewActivity?)?.getTravelUri().toString()
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        preferencesManager()
        taskViewModel.loadData(true, travelUri, userEmail)

        recyclerview = binding.rvTask
        setRecyclerView()

        binding.btnAddTask.setOnClickListener {
            NewTaskDialog(null,false).show(childFragmentManager, "newTaskTag")
        }


    }
    private fun preferencesManager() {
        val preferences = context?.getSharedPreferences(USER, AppCompatActivity.MODE_PRIVATE)
        val email = preferences?.getString(USER_EMAIL, "")
        userEmail = email?.replace(".", "").toString()
    }
        private fun setRecyclerView() {
            taskViewModel.publicAllTask.observe(viewLifecycleOwner, Observer {
            recyclerview.apply {
                layoutManager = LinearLayoutManager(activity)
                recyclerview.adapter = TaskRecyclerViewAdapter(this@ToDoFragment, it)
            }
        })
    }

    private fun alertDialogForImageDelete(task: Task, position: Int) {
        val pictureDialog = AlertDialog.Builder(requireActivity())

        pictureDialog.setTitle("Would you like to remove the task")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->
                taskViewModel.remove(task)
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun editTask(task: Task) {
        NewTaskDialog(task,false).show(childFragmentManager, "editTaskTag")
    }

    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task.pid!!)
    }

    override fun removeTask(task: Task, position: Int) {
        alertDialogForImageDelete(task, position)
    }

}