package com.skylightdevelopers.android.udemystateofartandroidapp.di

import com.skylightdevelopers.android.udemystateofartandroidapp.networking.AnimalApi
import com.skylightdevelopers.android.udemystateofartandroidapp.networking.AnimalApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
open class ApiModule {

    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    @Provides
    fun provideAnimalApi(): AnimalApi {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addNetworkInterceptor(httpLoggingInterceptor)

        builder.followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)

        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalApi::class.java)
    }

    @Provides
    open fun provideAnimalApiService(): AnimalApiService {
        return AnimalApiService()
    }
}