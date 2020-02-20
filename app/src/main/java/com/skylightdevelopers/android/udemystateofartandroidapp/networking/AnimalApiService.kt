package com.skylightdevelopers.android.udemystateofartandroidapp.networking

import com.skylightdevelopers.android.udemystateofartandroidapp.di.DaggerApiComponent
import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData
import com.skylightdevelopers.android.udemystateofartandroidapp.model.ApiKey
import io.reactivex.Single
import javax.inject.Inject

class AnimalApiService {

    @Inject
    lateinit var api: AnimalApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    /*private fun getNewHttpClient(): OkHttpClient? {
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
        return builder.build()
    }*/

    fun getApiKey(): Single<ApiKey> {
        return api.getKey()
    }

    fun getAnimals(key: String): Single<List<AnimalData>> {
        return api.getAnimalData(key)
    }
}