package com.example.personalassistant.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.personalassistant.data.task.data_source.TaskDatabase
import com.example.personalassistant.data.task.repository.TaskRepositoryImpl
import com.example.personalassistant.data.wallet.data_source.WalletDatabase
import com.example.personalassistant.data.wallet.repository.WalletRepositoryImpl
import com.example.personalassistant.domain.task.TaskRepository
import com.example.personalassistant.domain.wallet.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            "tasks"
        ).enableMultiInstanceInvalidation()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideTaskRepository(
        @ApplicationContext context: Context,
        taskDatabase: TaskDatabase
    ): TaskRepository {
        return TaskRepositoryImpl(context, taskDatabase)
    }
}