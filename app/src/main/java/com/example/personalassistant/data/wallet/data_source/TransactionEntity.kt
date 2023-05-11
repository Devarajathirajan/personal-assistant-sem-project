package com.example.personalassistant.data.wallet.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_history")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: Int,
    val title: String,
    val amount: Int,
    val date: String,
    val time: String,
    val category: String
)