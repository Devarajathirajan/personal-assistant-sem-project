package com.example.personalassistant.presentation.task

import com.example.personalassistant.domain.task.Task

data class TaskUiState(
    val taskList: List<Task> = emptyList(),
    val openedCard: Int = 0
)
