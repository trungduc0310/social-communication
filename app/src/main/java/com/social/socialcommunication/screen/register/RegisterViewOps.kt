package com.social.socialcommunication.screen.register

import android.content.Context
import com.social.socialcommunication.base.fragment.FragmentPresenterViewOps
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.model.User

interface RegisterViewOps {
    interface ViewOps : FragmentViewOps {
        fun onShowLoading()
        fun onHideLoading()
        fun onRegisterSuccess()
        fun onRegisterFail(mess: String)
    }

    interface PresenterViewOps : FragmentPresenterViewOps {
        fun registerEmail(context: Context, user: User, password: String)
    }
}