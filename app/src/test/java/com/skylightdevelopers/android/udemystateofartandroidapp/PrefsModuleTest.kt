package com.skylightdevelopers.android.udemystateofartandroidapp

import android.app.Application
import com.skylightdevelopers.android.udemystateofartandroidapp.di.PrefsModule
import com.skylightdevelopers.android.udemystateofartandroidapp.util.SharedPreferenceHelper

class PrefsModuleTest(val mockPrefs: SharedPreferenceHelper) : PrefsModule() {

    override fun provideSharedPreferance(application: Application): SharedPreferenceHelper {
        return mockPrefs
    }
}