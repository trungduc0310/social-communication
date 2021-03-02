package com.social.socialcommunication.common

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.google.gson.Gson
import com.social.socialcommunication.model.User

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
        editor.apply()
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun putFloat(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun putLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun putAccount(value: User, token: String, uriAvt: Uri?) {
        editor.putString(Constant.USER, Gson().toJson(value))
        editor.putString(Constant.TOKEN, token)
        editor.putString(Constant.AVATAR, uriAvt.toString())
        editor.apply()
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

    fun getToken(): String {
        return getString(Constant.TOKEN)
    }

    fun getAccount(): User? {
        try {
            val body = getString(Constant.USER)
            return Gson().fromJson(body, User::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun getAvatar(): Uri? {
        try {
            val body = getString(Constant.AVATAR)
            return Uri.parse(body)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun isLogin(): Boolean {
        return getString(Constant.USER).isNotEmpty() && getString(Constant.TOKEN).isNotEmpty()
    }

    fun logout() {
        editor.remove(Constant.USER)
        editor.remove(Constant.TOKEN)
        editor.apply()
    }

}