package com.example.personalassistant.presentation.task.add_edit

sealed class AddTaskEvent {

    object AddNewMileStone: AddTaskEvent()

    object SaveTask: AddTaskEvent()

    data class RemoveSubTasks(val position: Int): AddTaskEvent()
}