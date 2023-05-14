package com.example.personalassistant.presentation.task.add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalassistant.domain.task.Milestone
import com.example.personalassistant.domain.task.Task
import com.example.personalassistant.domain.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(val repository: TaskRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddTaskEvent) {
        when (event) {
            AddTaskEvent.AddNewMileStone -> {
                val currentSubTasks = uiState.value.numberOfSubTasks
                val currentMileStoneList = uiState.value.mileStoneItems
                currentMileStoneList.add(MileStoneItemState())
                _uiState.value = uiState.value.copy(
                    numberOfSubTasks = currentSubTasks + 1,
                    mileStoneItems = currentMileStoneList
                )
            }
            AddTaskEvent.SaveTask -> {
                saveTask()
            }
            is AddTaskEvent.RemoveSubTasks -> {
                val currentSubTasks = uiState.value.numberOfSubTasks
                val currentMileStoneList = uiState.value.mileStoneItems
                currentMileStoneList.removeAt(event.position)
                _uiState.value = uiState.value.copy(
                    numberOfSubTasks = currentSubTasks - 1,
                    mileStoneItems = currentMileStoneList
                )
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = uiState.value.copy(
            title = title,
            titleError = title.isEmpty()
        )
    }

    fun updateMileStoneItem(position: Int, mileStoneItem: MileStoneItemState) {
        val list = uiState.value.mileStoneItems
        list[position] = mileStoneItem
        _uiState.value = uiState.value.copy(
            mileStoneItems = list
        )
    }

    private fun saveTask() {
        viewModelScope.launch {
            uiState.value.apply {
                var nameEmptyError = false
                val mileStoneTimeStamp = MutableList<String?>(5) {null}
                mileStoneItems.forEachIndexed { index, item ->
                    if (item.nameError) nameEmptyError = true
                    //Date set no time default 8:00 PM
                    if (item.date.isNotEmpty()) {
                        mileStoneTimeStamp[index] = "${item.date}T${item.time}"
                    }
                }
                if (titleError || nameEmptyError) return@launch
                val mileStoneList = mileStoneItems.mapIndexed() { index, it ->
                    Milestone(
                        it.name, false, mileStoneTimeStamp[index]
                    )
                }
                val task = Task(
                    title =  title, number_of_subtasks =  numberOfSubTasks,
                    mileStones = mileStoneList, timeStamp = System.currentTimeMillis()
                )
                repository.addTask(task)
                _eventFlow.emit(UiEvent.SaveTask)
            }
        }
    }
}

sealed class UiEvent {
    object SaveTask : UiEvent()
}