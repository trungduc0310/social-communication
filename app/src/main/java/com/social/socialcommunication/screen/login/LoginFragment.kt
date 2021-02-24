package com.social.socialcommunication.screen.login

import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps

class LoginFragment : BaseFragment<LoginViewOps.PresenterViewOps>(), LoginViewOps.ViewOps {
    override fun getViewResoure(): Int {
        return R.layout.fragment_login
    }

    override fun setUp(view: View) {

    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return LoginPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

}