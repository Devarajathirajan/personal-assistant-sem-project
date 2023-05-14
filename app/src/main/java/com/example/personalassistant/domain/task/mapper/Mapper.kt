package com.example.personalassistant.domain.task.mapper

import com.example.personalassistant.data.task.data_source.TaskEntity
import com.example.personalassistant.domain.task.Task

fun Task.asEntity() = TaskEntity(
    id, title, number_of_subtasks,mileStones, timeStamp
)

fun TaskEntity.asModel() = Task(
    id, title, number_of_subtasks, mileStones, timeStamp
)