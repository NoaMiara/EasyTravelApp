package com.app.easy.travel.intarface

import com.app.easy.travel.model.Task

interface TaskItemClick {
    fun editTask(task: Task)
    fun completeTask(task: Task)
    fun removeTask(task: Task, position: Int)

}