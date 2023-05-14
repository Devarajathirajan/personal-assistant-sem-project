package com.example.personalassistant.domain.task


data class Task(
    val id: Int = 0,
    val title: String,
    val number_of_subtasks: Int,
    val mileStones: List<Milestone>,
    val timeStamp: Long
)

fun Task.getCompletion(): Int{
    var completed = 0f
    for (mileStone in mileStones) {
        if (mileStone.isCompleted) completed+=1
    }
    return ((completed/number_of_subtasks.toDouble())*100).toInt()
}