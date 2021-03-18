package com.social.socialcommunication.screen.list_contact

import com.social.socialcommunication.base.fragment.FragmentPresenterViewOps
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.model.User

interface ListContactViewOps {
    interface ViewOps : FragmentViewOps {
        fun getListUserSuccess(listUser: ArrayList<User>)
        fun getListUserFail(mess: String)

    }

    interface PresenterViewOps : FragmentPresenterViewOps {
        fun readUser(idUserLogin: String)

    }
}