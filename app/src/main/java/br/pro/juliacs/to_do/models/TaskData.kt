package br.pro.juliacs.to_do.models

class TaskData (
    val id: Long = 0,
    val description: String,
    val isUrgent: Boolean,
    var isDone: Boolean = false
)
