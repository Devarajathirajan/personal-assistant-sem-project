package com.example.personalassistant.data.task.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("Select * from task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("Select * from task where id=:id LIMIT 1")
    fun getTaskById(id: Int): TaskEntity

    @Update
    fun updateTask(task: TaskEntity)
}