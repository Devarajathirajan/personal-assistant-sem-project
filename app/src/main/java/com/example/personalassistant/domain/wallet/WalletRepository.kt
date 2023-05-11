package com.example.personalassistant.domain.wallet

import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun getTransactions(): Flow<List<Transaction>>

    suspend fun addTransaction(transaction: Transaction)

    suspend fun getBalanceCheck(): Flow<BalanceCheck>
}