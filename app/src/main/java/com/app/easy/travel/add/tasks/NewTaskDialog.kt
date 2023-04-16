package com.app.easy.travel.add.tasks

import android.os.Bundle
import android.text.Editable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.easy.travel.databinding.FragmentNewTaskDialogBinding

import com.app.easy.travel.model.Task
import java.util.*

class NewTaskDialog(private var task: Task?, private var isActivity: Boolean) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskDialogBinding
    private lateinit var taskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (task != null) {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.edTask.text = editable.newEditable(task?.task)
        } else {
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = if (isActivity) ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        else ViewModelProvider(requireParentFragment())[TaskViewModel::class.java]

        binding.btnSave.setOnClickListener {
            saveAction()
        }

    }

    private fun saveAction() {
        val taskDesc = binding.edTask.text.toString()
        if (task == null) {
            var pid = "${UUID.randomUUID()}"
            val newTask = Task(taskDesc, pid, false)
            taskViewModel.addTask(newTask)
        } else {
            taskViewModel.updateTask(task!!.pid!!, taskDesc, task!!.completed)
        }
        binding.edTask.setText("")
        dismiss()
    }
}
