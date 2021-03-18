package com.social.socialcommunication.screen.update_account

import android.app.Activity
import android.content.Context
import com.social.socialcommunication.base.fragment.FragmentPresenterViewOps
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.User

interface UpdateAccountViewOps {
    interface ViewOsp : FragmentViewOps {
        fun onUpdateSuccess()
        fun onUpdateFail(messenger: String)
    }

    interface PersenterViewOps : FragmentPresenterViewOps {
        fun updateProfile(context: Context, user: User)
        fun updatePassword(newPassword: String)
        fun updatePhoneNumber(activity: Activity, phoneNumber: String)
    }
}