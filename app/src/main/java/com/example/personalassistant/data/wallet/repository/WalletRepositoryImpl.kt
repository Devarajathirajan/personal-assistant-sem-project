package com.example.personalassistant.data.wallet.repository

import android.content.Context
import com.example.personalassistant.data.wallet.data_source.PreferenceKeys
import com.example.personalassistant.data.wallet.data_source.WalletDatabase
import com.example.personalassistant.data.wallet.data_source.dataStore
import com.example.personalassistant.data.wallet.data_source.setValue
import com.example.personalassistant.domain.wallet.BalanceCheck
import com.example.personalassistant.domain.wallet.Transaction
import com.example.personalassistant.domain.wallet.WalletRepository
import com.example.personalassistant.domain.wallet.mapper.asEntity
import com.example.personalassistant.domain.wallet.mapper.asModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class WalletRepositoryImpl(
    private val context: Context, private val walletDatabase: WalletDatabase
) : WalletRepository {

    override suspend fun getTransactions(): Flow<List<Transaction>> {
        return walletDatabase.transactionDao().getTransactions().map {
            it.map { entity ->
                entity.asModel()
            }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        walletDatabase.transactionDao().addTransaction(transaction.asEntity())
    }

    override suspend fun getBalanceCheck(): Flow<BalanceCheck> {
        return channelFlow {
            walletDatabase.transactionDao().getTransactions().collectLatest {
                var totalIncome = 0
                var totalExpense = 0
                it.forEach { transaction ->
                    when (transaction.type) {
                        0 -> totalIncome += transaction.amount
                        1 -> totalExpense += transaction.amount
                    }
                }
                val balance = totalIncome - totalExpense
                context.dataStore.setValue(PreferenceKeys.TOTAL_INCOME, totalIncome)
                context.dataStore.setValue(PreferenceKeys.TOTAL_EXPENSE, totalExpense)
                context.dataStore.setValue(PreferenceKeys.CURRENT_BALANCE, balance)
                send(
                    BalanceCheck(totalIncome, totalExpense, balance)
                )
            }
            awaitClose()
        }
    }
}