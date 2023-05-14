package com.example.personalassistant.domain.task

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getAllTasks(): Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun getTaskById(taskId: Int): Task

    suspend fun updateTasks(task: Task)
}