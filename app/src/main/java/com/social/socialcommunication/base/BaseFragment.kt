package com.social.socialcommunication.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.social.socialcommunication.common.CommonUtils
import java.lang.ref.WeakReference

public abstract class BaseFragment : Fragment() {
    private val TAG = BaseFragment::class.simpleName
    protected var mRootView: View? = null
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

    abstract fun getViewResoure(): Int
    abstract fun setUp(view: View): Int
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
}