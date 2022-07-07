package br.pro.juliacs.to_do.data

import androidx.compose.runtime.mutableStateListOf
import br.pro.juliacs.to_do.models.TaskData

object TasksSingleton {
    private val tasks = mutableStateListOf<TaskData>()

    fun updateTaskList(tasks: ArrayList<TaskData>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
    }


    fun getTasks(): List<TaskData> {
        return this.tasks
    }

    fun removeTask(taskData: TaskData) {
        this.tasks.remove(taskData)
    }
}
