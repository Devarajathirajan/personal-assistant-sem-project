package com.example.personalassistant.data.wallet.data_source

import androidx.datastore.preferences.core.intPreferencesKey

object PreferenceKeys {
    val TOTAL_INCOME = intPreferencesKey("total_income")
    val TOTAL_EXPENSE = intPreferencesKey("total_expense")
    val CURRENT_BALANCE = intPreferencesKey("current_balance")
}