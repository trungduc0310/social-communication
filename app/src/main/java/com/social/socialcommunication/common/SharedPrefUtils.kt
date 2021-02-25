package com.social.socialcommunication.common

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtils {
    private var context: Context? = null
    private lateinit var editor: SharedPreferences.Editor
    private var sharedPreferences: SharedPreferences? = null

    constructor(context: Context) {
        this.context = context
        sharedPreferences =
            this.context!!.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    companion object {
        private const val SHARED_PREFERENCE_NAME = "product_shared_pref"
        fun getInstance(context: Context): SharedPrefUtils {
            return SharedPrefUtils(context)
        }
    }

    fun clear() {
        editor!!.clear().apply()
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value)
    }

    fun putFloat(key: String, value: Float) {
        editor.putFloat(key, value)
    }

    fun putLong(key: String, value: Long) {
        editor.putLong(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
    }

    fun getString(key: String): String {
        return sharedPreferences?.getString(key, "").toString()
    }

    fun getInt(key: String): Int? {
        return sharedPreferences?.getInt(key, 0)
    }

    fun getFloat(key: String): Float? {
        return sharedPreferences?.getFloat(key, 0F)
    }

    fun getLong(key: String): Long? {
        return sharedPreferences?.getLong(key, 0)
    }

    fun getBoolean(key: String): Boolean? {
        return sharedPreferences?.getBoolean(key, false)
    }

}