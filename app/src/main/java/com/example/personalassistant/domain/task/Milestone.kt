package com.example.personalassistant.domain.task

data class Milestone(
    val name: String,
    var isCompleted: Boolean,
    val deadLine: String? = null
)