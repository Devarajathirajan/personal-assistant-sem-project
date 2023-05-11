package com.example.personalassistant.presentation.wallet

import com.example.personalassistant.domain.wallet.Transaction

data class WalletUiState(
    val income: String = "0.00",
    val expense: String = "0.00",
    val transactions: List<Transaction> = emptyList()
)