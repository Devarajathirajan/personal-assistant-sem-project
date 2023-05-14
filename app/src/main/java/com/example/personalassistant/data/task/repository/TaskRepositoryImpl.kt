package com.example.personalassistant.data.task.repository

import android.content.Context
import com.example.personalassistant.data.task.data_source.TaskDatabase
import com.example.personalassistant.domain.task.Milestone
import com.example.personalassistant.domain.task.Task
import com.example.personalassistant.domain.task.TaskRepository
import com.example.personalassistant.domain.task.mapper.asEntity
import com.example.personalassistant.domain.task.mapper.asModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(val context: Context, private val db: TaskDatabase): TaskRepository {
    override suspend fun getAllTasks(): Flow<List<Task>> {
        return db.taskDao().getAllTasks().map {
            it.map { entity->
                entity.asModel()
            }
        }
    }

    override suspend fun addTask(task: Task) {
        db.taskDao().insertTask(task.asEntity())
    }

    override suspend fun getTaskById(taskId: Int): Task {
        return db.taskDao().getTaskById(taskId).asModel()
    }

    override suspend fun updateTasks(task: Task) {
        db.taskDao().updateTask(task.asEntity())
    }

    override fun getMileStoneList(): Flow<List<Milestone>> {
        return db.taskDao().getAllTasks().map {
            val mileStoneList = mutableListOf<Milestone>()
            it.map { task ->
                mileStoneList.addAll(task.mileStones)
            }
            mileStoneList
        }
    }
}