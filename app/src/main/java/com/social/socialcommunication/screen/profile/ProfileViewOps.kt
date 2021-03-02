package com.social.socialcommunication.screen.profile

import android.content.Context
import com.social.socialcommunication.base.fragment.FragmentPresenterViewOps
import com.social.socialcommunication.base.fragment.FragmentViewOps

interface ProfileViewOps {
    interface ViewOps : FragmentViewOps {
    }

    interface PresenterViewOps : FragmentPresenterViewOps {
        fun logoutApp(context: Context)
    }
}