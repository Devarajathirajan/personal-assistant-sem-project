package com.example.personalassistant.data.wallet.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionEntityDao {

    @Query("SELECT * FROM transaction_history")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transactionEntity: TransactionEntity)
}