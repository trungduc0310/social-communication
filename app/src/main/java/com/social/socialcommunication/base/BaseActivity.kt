package com.social.socialcommunication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.social.socialcommunication.dialog.LoadingDialog

public abstract class BaseActivity : AppCompatActivity() {
    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewResoure())
        setUp()
    }

    abstract fun getViewResoure(): Int
    abstract fun setUp()

    fun showLoading() {
        try {
            if (mLoadingDialog != null) {
                mLoadingDialog?.dismiss()
            }
            mLoadingDialog = LoadingDialog()
            mLoadingDialog?.show(supportFragmentManager, mLoadingDialog?.javaClass?.simpleName)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun hideLoading() {
        try {
            if (mLoadingDialog != null) {
                mLoadingDialog?.dismiss()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}