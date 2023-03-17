package com.app.easy.travel.add.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.adapter.TaskRecyclerViewAdapter
import com.app.easy.travel.databinding.ActivityAddTaskBinding
import com.app.easy.travel.intarface.TaskItemClick
import com.app.easy.travel.model.Task

class AddTaskActivity : AppCompatActivity(), TaskItemClick {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBarManager()
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerview = binding.rvTask
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        taskViewModel.loadData(false, null, null)

        binding.btnAddTask.setOnClickListener {
            NewTaskDialog(null,true).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun actionBarManager() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Tasks"
    }


    private fun setRecyclerView() {
        taskViewModel.publicAllTask.observe(this) {
            recyclerview.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                recyclerview.adapter = TaskRecyclerViewAdapter(this@AddTaskActivity, it)
            }
        }
    }

    private fun alertDialogForImageDelete(task: Task, position: Int) {
        val pictureDialog = AlertDialog.Builder(this)

        pictureDialog.setTitle("Would you like to remove this task")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }.setPositiveButton("Delete") { _, _ ->

                taskViewModel.remove(task)

            }.show()
    }

    override fun editTask(task: Task) {
        NewTaskDialog(task,true).show(supportFragmentManager, "editTaskTag")
    }

    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task.pid!!)
    }

    override fun removeTask(task: Task, position: Int) {
        alertDialogForImageDelete(task, position)
    }
}