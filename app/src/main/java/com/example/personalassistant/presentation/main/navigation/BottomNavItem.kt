package com.example.personalassistant.presentation.main.navigation

import com.example.personalassistant.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home: BottomNavItem("Home", R.drawable.bnav_icon_home, "home")
    object Tasks: BottomNavItem("Tasks", R.drawable.bnav_icon_task, "tasks")
    object Event: BottomNavItem("Events", R.drawable.bnav_icon_event, "events")
    object Wallet: BottomNavItem("Wallet", R.drawable.bnav_icon_wallet, "wallet")
}