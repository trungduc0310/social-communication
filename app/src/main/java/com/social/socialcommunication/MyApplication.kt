package com.social.socialcommunication

import android.content.Context
import android.graphics.Typeface
import android.os.AsyncTask
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {
    var typefaceLight: Typeface? = null
    var typefaceRegular: Typeface? = null
    var typeFaceMedium: Typeface? = null
    var typefaceItalic: Typeface? = null
    var typefaceBold: Typeface? = null

    companion object {
        private var context: Context? = null
        fun getContext(): Context {
            return context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        val asynTask = object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                typefaceLight =
                    Typeface.createFromAsset(assets, "quicksand/quicksand_light.ttf")
                typefaceRegular =
                    Typeface.createFromAsset(assets, "quicksand/quicksand_regular.ttf")
                typeFaceMedium =
                    Typeface.createFromAsset(assets, "quicksand/quicksand_medium.ttf")
                typefaceBold =
                    Typeface.createFromAsset(assets, "quicksand/quicksand_bold.ttf")
                return null
            }

        }
        asynTask.execute()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}