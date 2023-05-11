package com.example.personalassistant.domain.wallet

data class Transaction(
    val id: Int = 0,
    val type: TransactionType,
    val title: String,
    val amount: Int,
    val date: String,
    val time: String,
    val category: String
)

enum class TransactionType {
    INCOME, EXPENSE
}