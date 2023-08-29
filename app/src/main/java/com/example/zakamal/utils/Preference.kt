package com.example.zakamal.utils

import android.content.Context
import android.content.SharedPreferences

class Preference(val context: Context) {
    companion object {
        const val USER_PREF = "USER_PREF"
    }

    var sharedPreferences = context.getSharedPreferences(USER_PREF, 0)

    fun setLogin(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setLoginInt(key: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getValuesInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }
}