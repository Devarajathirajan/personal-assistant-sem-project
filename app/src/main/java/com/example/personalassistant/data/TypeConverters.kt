package com.example.personalassistant.data

import androidx.room.TypeConverter
import com.example.personalassistant.domain.task.Milestone
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {

    @TypeConverter
    fun fromMileStoneList(value: List<Milestone>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringMilestone(value: String): List<Milestone> {
        return try {
            Gson().fromJson(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)