package com.skylightdevelopers.android.udemystateofartandroidapp.util

import android.content.Context

class SharedPreferenceHelper(context: Context) {

    private val PREF_KEY_VAL = "API_KEY"
    private val PREFS_NAME = "com.skylightdevelopers.android.udemystateofartandroidapp"

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME,0)

    fun saveKey(key: String?) {
        prefs.edit().putString(PREF_KEY_VAL, key).apply()
    }

    fun getKey(): String? {
        return prefs.getString(PREF_KEY_VAL, null)
    }
}