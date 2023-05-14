package com.example.personalassistant.presentation.task.add_edit

data class AddTaskUiState(

    val title: String = "",
    val titleError: Boolean = true,

    val numberOfSubTasks:Int = 1,

    val mileStoneItems: MutableList<MileStoneItemState> = mutableListOf(
        MileStoneItemState()
    )
)

data class MileStoneItemState(
    val name: String = "",
    val nameError: Boolean = true,

    val date: String = "",
    val time: String = "20:00"
)