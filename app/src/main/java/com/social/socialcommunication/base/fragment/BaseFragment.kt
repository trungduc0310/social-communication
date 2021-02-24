package com.social.socialcommunication.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.social.socialcommunication.base.PresenterFactory
import com.social.socialcommunication.base.activity.BaseActivity
import com.social.socialcommunication.common.CommonUtils
import com.tuanfadbg.takephotoutils.TakePhotoUtils
import java.lang.ref.WeakReference

public abstract class BaseFragment<P : FragmentPresenterViewOps> : Fragment(), FragmentViewOps {
    private val TAG = BaseFragment::class.simpleName
    protected var mRootView: View? = null
    public var mPresenter: P? = null

    companion object {
        private var PRESENTER_ID: String = ""
    }

    private lateinit var mWeakRef: WeakReference<Activity>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mWeakRef = WeakReference<Activity>(activity as Activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            PRESENTER_ID = javaClass.simpleName
            registerPresenter()
            initPresenter()
            if (mPresenter != null)
                mPresenter?.onCreate()
            return if (mRootView == null) {
                mRootView = inflater.inflate(getViewResoure(), container, false)
                mRootView?.isClickable = true
                mRootView?.setOnTouchListener { v, event ->
                    if (v !is EditText)
                        CommonUtils.hideSoftKeyboard(activity!!)
                    true
                }
                mRootView?.isFocusableInTouchMode = true
                setUp(mRootView!!)
                mRootView
            } else {
                mRootView
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return inflater.inflate(getViewResoure(), container, false)
    }

    override fun onPause() {
        super.onPause()
        if (mPresenter != null) {
            mPresenter?.onPause()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            if (mPresenter != null) {
                mPresenter?.onResumeVisible()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.onDestroy()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mPresenter != null) {
            mPresenter?.onResume()
        }
    }

    override fun getApplicationContext(): Context? {
        try {
            val activity = activity as BaseActivity<*>
            return activity.applicationContext
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun getActivityContext(): Context? {
        try {
            val activity = activity as BaseActivity<*>
            return activity.getActivityContext()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun getTakePhotoUtils(): TakePhotoUtils? {
        try {
            val activity = activity as BaseActivity<*>
            return activity.getTakePhotoUtils()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun showProgressbar() {
        try {
            val activity = activity as BaseActivity<*>
            activity.showLoading()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun hideProgressbar() {
        try {
            val activity = activity as BaseActivity<*>
            activity.hideLoading()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun showToast(mess: String) {
        try {
            val activity = activity as BaseActivity<*>
            activity.showToast(mess)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
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
        try {
            val activity = activity as BaseActivity<*>
            return activity.addFragment(
                manager,
                fragment,
                res,
                addBackStack,
                enter,
                exit,
                popEnter,
                popExit
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun addFragment(fragment: Fragment, addBackStack: Boolean) {
        try {
            val activity = activity as BaseActivity<*>
            activity.addFragment(fragment, addBackStack)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun replaceFragment(
        manager: FragmentManager,
        fragment: Fragment,
        res: Int,
        addBackStack: Boolean,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    ) {
        try {
            val activity = activity as BaseActivity<*>
            activity.replaceFragment(
                manager,
                fragment,
                res,
                addBackStack,
                enter,
                exit,
                popEnter,
                popExit
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun replaceFragment(
        manager: FragmentManager,
        fragment: Fragment,
        addBackStack: Boolean,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    ) {
        try {
            val activity = activity as BaseActivity<*>
            activity.replaceFragment(
                manager,
                fragment,
                addBackStack,
                enter,
                exit,
                popEnter,
                popExit
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun replaceFragment(
        manager: FragmentManager,
        fragment: Fragment,
        layout: Int,
        addBackStack: Boolean
    ) {
        try {
            val activity = activity as BaseActivity<*>
            activity.replaceFragment(
                manager,
                fragment,
                layout,
                addBackStack
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun replaceFragment(fragment: Fragment, layout: Int, addBackStack: Boolean) {
        try {
            val activity = activity as BaseActivity<*>
            activity.replaceFragment(
                fragment,
                layout,
                addBackStack
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun replaceFragment(fragment: Fragment, addBackStack: Boolean) {
        try {
            val activity = activity as BaseActivity<*>
            activity.replaceFragment(
                fragment,
                addBackStack
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        try {
            val activity = activity as BaseActivity<*>
            activity.removeFragment(fragmentManager, fragment)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun popBackStack() {
        try {
            val activity = activity as BaseActivity<*>
            activity.popBackStack()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun popBackStack(manager: FragmentManager, tag: String) {
        try {
            val activity = activity as BaseActivity<*>
            activity.popBackStack(manager, tag)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun popBackStack(tag: String) {
        try {
            val activity = activity as BaseActivity<*>
            activity.popBackStack(tag)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun popBackStack(manager: FragmentManager) {
        try {
            val activity = activity as BaseActivity<*>
            activity.popBackStack(manager)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun clearBackStack() {
        try {
            val activity = activity as BaseActivity<*>
            activity.clearBackStack()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    abstract fun getViewResoure(): Int
    abstract fun setUp(view: View)

    fun runOnUIThread(runnable: Runnable) {
        if (activity != null) {
            activity?.runOnUiThread(runnable)
        }
    }

    fun getWeakRef(): WeakReference<Activity> {
        return mWeakRef
    }

    fun getActivityReference(): Activity? {
        return if (mWeakRef != null) mWeakRef.get() else null
    }

    fun registerPresenter() {
        PresenterFactory.getInstance()
            .registerPresenter(javaClass.simpleName, onRegisterPresenter())
    }

    fun initPresenter() {
        mPresenter = PresenterFactory.getInstance().createPresenter(PRESENTER_ID, this) as P
    }

    protected abstract fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>>
}