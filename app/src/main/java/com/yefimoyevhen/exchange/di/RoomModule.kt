package com.yefimoyevhen.exchange.di

import android.content.Context
import androidx.room.Room
import com.yefimoyevhen.exchange.database.ExchangeDao
import com.yefimoyevhen.exchange.database.ExchangeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideExchangeDb(@ApplicationContext context: Context): ExchangeDatabase {
        return Room
            .databaseBuilder(
                context,
                ExchangeDatabase::class.java,
                ExchangeDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideExchangeDAO(exchangeDatabase: ExchangeDatabase): ExchangeDao {
        return exchangeDatabase.exchangeDao()
    }
}