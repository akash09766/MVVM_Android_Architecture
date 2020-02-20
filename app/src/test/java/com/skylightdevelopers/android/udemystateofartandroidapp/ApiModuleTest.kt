package com.skylightdevelopers.android.udemystateofartandroidapp

import com.skylightdevelopers.android.udemystateofartandroidapp.di.ApiModule
import com.skylightdevelopers.android.udemystateofartandroidapp.networking.AnimalApiService

class ApiModuleTest(val mockApiService: AnimalApiService) : ApiModule() {

    override fun provideAnimalApiService(): AnimalApiService {
        return mockApiService
    }
}