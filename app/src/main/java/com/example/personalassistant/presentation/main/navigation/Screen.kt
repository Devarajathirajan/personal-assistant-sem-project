package com.example.personalassistant.presentation.main.navigation

sealed class Screen(var screen_route: String) {
    object AddTransaction: Screen("add_transaction")
    object AddTask: Screen("add_task")
}
