package com.app.easy.travel.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.R
import com.app.easy.travel.intarface.TaskItemClick
import com.app.easy.travel.model.Task

class TaskRecyclerViewAdapter(
    private val recyclerViewTaskItemClick: TaskItemClick,
    private val taskList: List<Task> = ArrayList()
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taskViewModel = taskList[position]
        holder.taskView.text = taskViewModel.task

        if(taskViewModel.completed){
            holder.taskView.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.completeButton.setImageResource(taskViewModel.imageResource())
        holder.completeButton.setOnClickListener {
            recyclerViewTaskItemClick.completeTask(taskViewModel)
        }
        holder.taskView.setOnClickListener {
            recyclerViewTaskItemClick.editTask(taskViewModel)
        }
        holder.taskView.setOnLongClickListener{
            recyclerViewTaskItemClick.removeTask(taskViewModel,position)
            return@setOnLongClickListener true
        }


    }

    override fun getItemCount(): Int {
        return taskList.size

    }
    fun remove(position: Int) {
        notifyItemRemoved(position)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskView: TextView = itemView.findViewById(R.id.task_info)
        val completeButton: ImageButton = itemView.findViewById(R.id.completeButton)
    }


}