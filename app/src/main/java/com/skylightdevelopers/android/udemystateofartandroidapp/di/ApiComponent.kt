package com.skylightdevelopers.android.udemystateofartandroidapp.di

import com.skylightdevelopers.android.udemystateofartandroidapp.networking.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(apiService: AnimalApiService)
}