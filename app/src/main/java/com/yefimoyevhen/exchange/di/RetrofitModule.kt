package com.yefimoyevhen.exchange.di


import com.yefimoyevhen.exchange.api.ExchangeApi
import com.yefimoyevhen.exchange.util.API_KEY
import com.yefimoyevhen.exchange.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newUrl = originalRequest.url.newBuilder()
                    .addQueryParameter(
                        "access_key", API_KEY
                    ).build()
                val builder = originalRequest.newBuilder().url(newUrl)
                chain.proceed(builder.build())
            }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): ExchangeApi {
        return retrofit
            .build()
            .create(ExchangeApi::class.java)
    }
}