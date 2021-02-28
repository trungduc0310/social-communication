package com.social.socialcommunication.screen.register

import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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

    override fun onShowLoading() {
        TODO("Not yet implemented")
    }

    override fun onHideLoading() {
        TODO("Not yet implemented")
    }

    override fun onRegisterSuccess() {
        TODO("Not yet implemented")
    }

    override fun onRegisterFail(mess: String) {
        TODO("Not yet implemented")
    }
}