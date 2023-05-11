package com.example.personalassistant.domain.wallet.mapper

import com.example.personalassistant.data.wallet.data_source.TransactionEntity
import com.example.personalassistant.domain.wallet.Transaction
import com.example.personalassistant.domain.wallet.TransactionType

fun Transaction.asEntity(): TransactionEntity {
    val res = if (type == TransactionType.INCOME) 0 else 1
    return TransactionEntity(id, res, title, amount, date, time, category)
}

fun TransactionEntity.asModel(): Transaction {
    val res = if (type == 0) TransactionType.INCOME else TransactionType.EXPENSE
    return Transaction(
        id, res, title, amount, date, time, category
    )
}