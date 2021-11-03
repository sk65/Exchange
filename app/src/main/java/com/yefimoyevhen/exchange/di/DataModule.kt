package com.yefimoyevhen.exchange.di

import android.content.Context
import com.yefimoyevhen.exchange.api.ExchangeApi
import com.yefimoyevhen.exchange.database.ExchangeDao
import com.yefimoyevhen.exchange.datasourse.LocalDataSource
import com.yefimoyevhen.exchange.datasourse.RemoteDataSource
import com.yefimoyevhen.exchange.util.PREF_KEY
import com.yefimoyevhen.exchange.util.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesLocalDataSource(exchangeDao: ExchangeDao) =
        LocalDataSource(exchangeDao)

    @Provides
    @Singleton
    fun providesRemoteDataSource(exchangeApi: ExchangeApi) =
        RemoteDataSource(exchangeApi)

    @Provides
    @Singleton
    fun providesShearedPreferencesManager(@ApplicationContext context: Context) =
        SharedPreferencesManager(
            context.getSharedPreferences(
                PREF_KEY,
                Context.MODE_PRIVATE
            )
        )

}