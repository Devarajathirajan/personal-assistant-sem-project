package com.example.personalassistant.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalassistant.domain.task.Task
import com.example.personalassistant.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()

    private var getTasksJob: Job? = null

    init {
        getTasks()
    }

    private fun getTasks() {
        getTasksJob?.cancel()
        getTasksJob = viewModelScope.launch {
            repository.getAllTasks().collectLatest() {
                _uiState.value = _uiState.value.copy(
                    taskList = it
                )
            }
        }
    }
    fun updateOpenedCardState(index: Int) {
        _uiState.value = _uiState.value.copy(
            openedCard = index
        )
    }
    fun updateMileStone(isCompleted: Boolean, mileStoneIndex: Int, index: Int, task: Task) {
        viewModelScope.launch {
            val newTaskList = uiState.value.taskList as MutableList
            val newList = newTaskList[index].mileStones as MutableList
            newList[mileStoneIndex].isCompleted = isCompleted
            newTaskList[index] = task.copy(
                mileStones = newList
            )
            repository.updateTasks(newTaskList[index])
            // removing below code not triggering composition
            newTaskList.removeAt(index)
            _uiState.value = _uiState.value.copy(
                taskList = newTaskList
            )
        }
    }
}