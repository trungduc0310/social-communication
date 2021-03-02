package com.social.socialcommunication.screen.profile

import android.content.Context
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.SharedPrefUtils

class ProfileUserPresenter : FragmentPresenter<ProfileViewOps.ViewOps>(),
    ProfileViewOps.PresenterViewOps {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
    }

    override fun logoutApp(context: Context) {
        mAuth.signOut()
        SharedPrefUtils.getInstance(context).clear()
    }

}