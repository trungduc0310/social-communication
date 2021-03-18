package com.social.socialcommunication.screen.login

import android.content.Context
import com.social.socialcommunication.base.BasePresenterOps
import com.social.socialcommunication.base.BaseViewOps
import com.social.socialcommunication.base.fragment.FragmentPresenterViewOps
import com.social.socialcommunication.base.fragment.FragmentViewOps

interface LoginViewOps {
    interface ViewOps : FragmentViewOps {
        fun onLoginSuccess()
        fun onLoginFail(mess: String)
        fun onShowLoading()
        fun onHideLoading()
    }

    interface PresenterViewOps : FragmentPresenterViewOps {
        fun loginEmail(context: Context, email: String, password: String)
        fun loginGmailWithToken(context: Context, token: String)
        fun forgotPassword(email: String)
    }
}