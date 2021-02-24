package com.social.socialcommunication.screen.register

import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps

class RegisterFragment : BaseFragment<RegisterViewOps.PresenterViewOps>(), RegisterViewOps.ViewOps {
    override fun getViewResoure(): Int {
        return R.layout.fragment_register
    }

    override fun setUp(view: View) {
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return RegisterPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }
}