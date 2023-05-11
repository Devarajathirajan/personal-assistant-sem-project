package com.example.personalassistant.presentation.wallet.add_edit

data class AddTransactionUiState(
    val title: String = "",
    val titleEmptyError: Boolean = true,

    val type: String = "Income", //Default Income

    val date: String = "",
    val dateEmptyError: Boolean = true,

    val time: String = "",
    val timeEmptyError: Boolean = true,

    val category: String = "",
    val categoryEmptyError: Boolean = true,

    val amount: String = "",
    val amountInvalidError: Boolean = true
)