package com.example.personalassistant.presentation.home

data class HomeUiState(
    val income: String = "0.0",
    val expense: String = "0.0",
    val balance: String = "0.0",

    val today_milestones: Int = 0,
    val completed_milestones: Int = 0,

    val today_meetings: Int = 0,
    val this_week_meetings: Int = 0
)