package com.example.personalassistant.data.wallet.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class WalletDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionEntityDao
}