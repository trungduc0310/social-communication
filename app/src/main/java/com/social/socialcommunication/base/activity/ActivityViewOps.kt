package com.social.socialcommunication.base.activity

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.social.socialcommunication.base.BaseViewOps
import com.tuanfadbg.takephotoutils.TakePhotoUtils

public interface ActivityViewOps : BaseViewOps {
    fun getActivityContext(): Context
    fun getApplicationContext(): Context
    fun getTakePhotoUtils(): TakePhotoUtils
    fun showProgressbar()
    fun hideProgressbar()
    fun showToast(mess: String)
    fun addFragment(fragment: Fragment, addBackStack: Boolean)
    fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment)
    fun replaceFragment(fragment: Fragment, addBackStack: Boolean)
    fun replaceFragment(fragment: Fragment, layout: Int, addBackStack: Boolean)

    fun replaceFragment(
        manager: FragmentManager,
        fragment: Fragment,
        layout: Int,
        addBackStack: Boolean
    )

    fun replaceFragment(
        manager: FragmentManager,
        fragment: Fragment,
        addBackStack: Boolean,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    )

    fun replaceFragment(
        manager: FragmentManager,
        fragment: Fragment,
        res: Int,
        addBackStack: Boolean,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    )

    fun addFragment(
        manager: FragmentManager,
        fragment: Fragment,
        res: Int,
        addBackStack: Boolean,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    )

    fun popBackStack(manager: FragmentManager)

    fun popBackStack()

    fun popBackStack(
        manager: FragmentManager,
        tag: String
    )

    fun popBackStack(tag: String)

    fun clearBackStack()
}