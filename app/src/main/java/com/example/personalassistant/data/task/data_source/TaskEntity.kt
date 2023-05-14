package com.example.personalassistant.data.task.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.personalassistant.domain.task.Milestone

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val number_of_subtasks: Int,
    val mileStones: List<Milestone>,
    val timeStamp: Long
)