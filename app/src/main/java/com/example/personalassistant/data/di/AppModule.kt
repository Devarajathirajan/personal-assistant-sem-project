package com.example.personalassistant.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.personalassistant.data.wallet.data_source.WalletDatabase
import com.example.personalassistant.data.wallet.repository.WalletRepositoryImpl
import com.example.personalassistant.domain.wallet.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideWalletDatabase(app: Application): WalletDatabase {
        return Room.databaseBuilder(
            app,
            WalletDatabase::class.java,
            "wallet"
        ).enableMultiInstanceInvalidation()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideWalletRepository(
        @ApplicationContext context: Context,
        walletDatabase: WalletDatabase
    ): WalletRepository {
        return WalletRepositoryImpl(context, walletDatabase)
    }
}