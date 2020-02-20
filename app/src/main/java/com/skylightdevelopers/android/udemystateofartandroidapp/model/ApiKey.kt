package com.skylightdevelopers.android.udemystateofartandroidapp.model


import com.google.gson.annotations.SerializedName

data class ApiKey(
    @SerializedName("key")
    val key: String?,
    @SerializedName("message")
    val message: String?
)