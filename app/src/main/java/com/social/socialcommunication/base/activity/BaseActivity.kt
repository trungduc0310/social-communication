package com.social.socialcommunication.base.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.social.socialcommunication.BuildConfig
import com.social.socialcommunication.R
import com.social.socialcommunication.base.PresenterFactory
import com.social.socialcommunication.common.CommonUtils
import com.social.socialcommunication.dialog.LoadingDialog
import com.tuanfadbg.takephotoutils.TakePhotoUtils

public abstract class BaseActivity<P : ActivityPresenterViewOps> : AppCompatActivity(),
    ActivityViewOps {
    public var mPresenter: P? = null
    private var takePhotoUtils: TakePhotoUtils? = null
    private lateinit var mSnackBar: Snackbar
    fun getPresenter(): P {
        return mPresenter!!
    }

    companion object {
        private var PRESENTER_ID: String? = null
    }

    private var mLoadingDialog: LoadingDialog? = null

    private val mConnectReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1?.action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (!CommonUtils.hasConnected(p0!!)) {
                    if (mSnackBar != null) {
                        mSnackBar.show()
                    }
                } else {
                    if (mSnackBar != null) {
                        mSnackBar.dismiss()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewResoure())
        try {
            PRESENTER_ID = this::class.simpleName
            registerPresenter()
            takePhotoUtils = TakePhotoUtils(this, BuildConfig.APPLICATION_ID + ".provider")
            mPresenter?.onCreate()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        setSnackBar()
        setUp()
    }

    override fun onResume() {
        super.onResume()
        if (mSnackBar != null) {
            if (CommonUtils.hasConnected(this)) mSnackBar.dismiss()
        }
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
            unregisterReceiver(mConnectReceiver)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun setSnackBar() {
        try {
            val view = window.decorView.findViewById<View>(android.R.id.content)
            if (view != null) {
                mSnackBar = Snackbar.make(
                    view,
                    getString(R.string.text_no_connect_internet),
                    Snackbar.LENGTH_INDEFINITE
                )
                registerReceiver(
                    mConnectReceiver,
                    IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun registerPresenter() {
        PresenterFactory.getInstance().registerPresenter(PRESENTER_ID!!, onRegisterPresenter())
    }

    private fun initPresenter() {
        mPresenter = PresenterFactory.getInstance().createPresenter(PRESENTER_ID!!, this) as P?
    }

    override fun getActivityContext(): Context {
        return this
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    override fun getTakePhotoUtils(): TakePhotoUtils {
        return takePhotoUtils!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        takePhotoUtils?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        takePhotoUtils?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showProgressbar() {
        showLoading()
    }

    override fun hideProgressbar() {
        hideLoading()
    }

    override fun showToast(mess: String) {
        runOnUiThread {
            kotlin.run {
                Toast.makeText(getActivityContext(), mess, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun replaceFragment(
        manager: FragmentManager, fragment: Fragment, res: Int,
        addBackStack: Boolean, enter: Int, exit: Int, popEnter: Int, popExit: Int
    ) {
        runOnUiThread {
            kotlin.run {
                try {
                    val fragmentTransaction = manager.beginTransaction()
                    if (enter != 0 && exit != 0 && popEnter != 0 && popExit != 0)
                        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
                    if (addBackStack) fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
                    fragmentTransaction.replace(res, fragment, fragment.javaClass.simpleName)
                    fragmentTransaction.commit()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    override fun replaceFragment(
        manager: FragmentManager, fragment: Fragment, addBackStack: Boolean,
        enter: Int, exit: Int, popEnter: Int, popExit: Int
    ) {
        replaceFragment(
            manager,
            fragment,
            android.R.id.content,
            addBackStack,
            enter,
            exit,
            popEnter,
            popExit
        )
    }

    override fun replaceFragment(
        manager: FragmentManager, fragment: Fragment, layout: Int, addBackStack: Boolean
    ) {
        replaceFragment(
            manager,
            fragment,
            layout,
            addBackStack,
            R.anim.from_right,
            R.anim.to_left,
            R.anim.from_left,
            R.anim.to_right
        )
    }

    override fun replaceFragment(fragment: Fragment, layout: Int, addBackStack: Boolean) {
        replaceFragment(
            supportFragmentManager, fragment, layout, addBackStack, R.anim.from_right,
            R.anim.to_left,
            R.anim.from_left,
            R.anim.to_right
        )
    }

    override fun replaceFragment(fragment: Fragment, addBackStack: Boolean) {
        replaceFragment(
            supportFragmentManager, fragment, android.R.id.content, addBackStack, R.anim.from_right,
            R.anim.to_left,
            R.anim.from_left,
            R.anim.to_right
        )
    }

    override fun addFragment(
        manager: FragmentManager,
        fragment: Fragment,
        res: Int,
        addBackStack: Boolean,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    ) {
        runOnUiThread {
            kotlin.run {
                try {
                    val fragmentTransaction = manager.beginTransaction()
                    if (enter != 0 && exit != 0 && popEnter != 0 && popExit != 0)
                        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
                    if (addBackStack) fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
                    fragmentTransaction.add(res, fragment, fragment.javaClass.simpleName)
                    fragmentTransaction.commit()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    override fun addFragment(fragment: Fragment, addBackStack: Boolean) {
        addFragment(
            supportFragmentManager, fragment, android.R.id.content, addBackStack, R.anim.from_right,
            R.anim.to_left,
            R.anim.from_left,
            R.anim.to_right
        )
    }

    override fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        runOnUiThread {
            kotlin.run {
                try {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setCustomAnimations(
                        R.anim.from_right,
                        R.anim.to_left,
                        R.anim.from_left,
                        R.anim.to_right
                    )
                    transaction.remove(fragment)
                    transaction.commit()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    override fun popBackStack() {
        runOnUiThread {
            kotlin.run {
                supportFragmentManager.popBackStack()
            }
        }
    }

    override fun popBackStack(manager: FragmentManager, tag: String) {
        runOnUiThread {
            kotlin.run {
                manager.popBackStack(tag, 0)
            }
        }
    }

    override fun popBackStack(tag: String) {
        runOnUiThread {
            kotlin.run {
                supportFragmentManager.popBackStack(tag, 0)
            }
        }
    }

    override fun popBackStack(manager: FragmentManager) {
        runOnUiThread {
            kotlin.run {
                manager.popBackStack()
            }
        }
    }

    override fun clearBackStack() {
        runOnUiThread {
            kotlin.run {
                val fm = supportFragmentManager
                val count = fm.backStackEntryCount
                for (i in 0 until count) {
                    fm.popBackStack()
                }
            }
        }
    }


    abstract fun getViewResoure(): Int
    abstract fun setUp()

    fun showLoading() {
        try {
            if (mLoadingDialog != null) {
                mLoadingDialog?.dismiss()
            }
            mLoadingDialog = LoadingDialog(this, android.R.style.Theme_Translucent_NoTitleBar)
            mLoadingDialog?.show()
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

    protected abstract fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>>


}