package com.skylightdevelopers.android.udemystateofartandroidapp.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: Application) {
    @Provides
    fun provideAppModule(): Application = app
}