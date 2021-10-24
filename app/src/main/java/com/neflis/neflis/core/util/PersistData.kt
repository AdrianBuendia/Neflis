package com.neflis.neflis.core.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PersistData(val context: Context?) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun clearPreferences(){
        this.sharedPreferences.edit().clear().apply()
    }

    fun setPreferenceStringByKey(key: String, value: String) {
        this.sharedPreferences.edit().putString(key, value).apply()
    }

    fun setPreferenceBooleanByKey(key: String, value: Boolean){
        this.sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun setPreferenceLongByKey(key: String, value: Long) {
        this.sharedPreferences.edit().putLong(key, value).apply()
    }

    fun setPreferenceIntByKey(key: String, value: Int) {
        this.sharedPreferences.edit().putInt(key, value).apply()
    }


    fun setPreferenceFloatByKey(key: String, value: Float) {
        this.sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getPreferenceFloatByKey(key: String) : Float {
        return this.sharedPreferences.getFloat(key, 0.0f)
    }

    fun getPreferenceStringByKey(key: String) : String{
        return this.sharedPreferences.getString(key, "").orEmpty()
    }

    fun getPreferenceBooleanByKey(key: String): Boolean{
        return this.sharedPreferences.getBoolean(key, false)
    }

    fun getPreferenceLongByKey(key: String): Long{
        return this.sharedPreferences.getLong(key, 0)
    }

    fun getPreferenceIntByKey(key: String): Int{
        return this.sharedPreferences.getInt(key, 0)
    }
}