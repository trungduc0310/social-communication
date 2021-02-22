package com.social.socialcommunication.base.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.social.socialcommunication.BuildConfig
import com.social.socialcommunication.R
import com.social.socialcommunication.base.PresenterFactory
import com.social.socialcommunication.dialog.LoadingDialog
import com.tuanfadbg.takephotoutils.TakePhotoUtils

public abstract class BaseActivity<P : ActivityPresenterViewOps> : AppCompatActivity(),
    ActivityViewOps {
    public var mPresenter: P? = null
    private var takePhotoUtils: TakePhotoUtils? = null
    fun getPresenter(): P {
        return mPresenter!!
    }

    companion object {
        private var PRESENTER_ID: String? = null
    }

    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewResoure())
        try {
            PRESENTER_ID = this::class.simpleName + System.currentTimeMillis()
            registerPresenter()
            takePhotoUtils = TakePhotoUtils(this, BuildConfig.APPLICATION_ID + ".provider")
            mPresenter?.onCreate()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        setUp()
    }

    private fun registerPresenter() {
        PresenterFactory.getInstance().registerPresenter(PRESENTER_ID!!, onRegisterPresenter())
    }

    private fun initPresenter() {
        mPresenter = PresenterFactory.getInstance().createPresenter(
            PRESENTER_ID!!,
            this
        ) as P?
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
            R.id.main_layout,
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
            supportFragmentManager, fragment, R.id.main_layout, addBackStack, R.anim.from_right,
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
            supportFragmentManager, fragment, R.id.main_layout, addBackStack, R.anim.from_right,
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

    protected abstract fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>>


}