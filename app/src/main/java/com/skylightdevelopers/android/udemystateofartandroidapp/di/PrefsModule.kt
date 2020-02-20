package com.skylightdevelopers.android.udemystateofartandroidapp.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.skylightdevelopers.android.udemystateofartandroidapp.util.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class PrefsModule {
    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_OF_TYPE_APP)
    open fun provideSharedPreferance(application: Application): SharedPreferenceHelper {
        return SharedPreferenceHelper(application)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_OF_TYPE_ACTIVITY)
    fun provideActivitySharedPreference(activity: AppCompatActivity): SharedPreferenceHelper {
        return SharedPreferenceHelper(activity)
    }
}

const val CONTEXT_OF_TYPE_APP = "type of app"
const val CONTEXT_OF_TYPE_ACTIVITY = "type of activity"

@Qualifier
annotation class TypeOfContext(val type : String)