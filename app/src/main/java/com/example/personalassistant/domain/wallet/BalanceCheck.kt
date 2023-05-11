package com.example.personalassistant.domain.wallet

data class BalanceCheck(
    val income: Int,
    val expense: Int,
    val balance: Int
)