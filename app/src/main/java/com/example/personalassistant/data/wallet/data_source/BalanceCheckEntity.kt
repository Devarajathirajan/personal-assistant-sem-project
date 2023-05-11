package com.example.personalassistant.data.wallet.data_source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(
    name = "finify_balance_preferences"
)

fun <T> DataStore<Preferences>.getValue(
    key: Preferences.Key<T>,
    defValue: T
): Flow<T> {
    return data.map { preferences ->
        preferences[key] ?: defValue
    }
}

suspend fun <T> DataStore<Preferences>.setValue(
    key: Preferences.Key<T>,
    value: T
) {
    edit { preferences ->
        preferences[key] = value
    }
}