package com.skylightdevelopers.android.udemystateofartandroidapp.networking

import com.skylightdevelopers.android.udemystateofartandroidapp.model.AnimalData
import com.skylightdevelopers.android.udemystateofartandroidapp.model.ApiKey
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("getKey")
    fun getKey(): Single<ApiKey>

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimalData(@Field("key") key: String?): Single<List<AnimalData>>
}