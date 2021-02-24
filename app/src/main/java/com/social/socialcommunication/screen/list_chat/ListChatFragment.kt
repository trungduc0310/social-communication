package com.social.socialcommunication.screen.list_chat

import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps

class ListChatFragment : BaseFragment<ListChatViewOps.PresenterViewOps>(), ListChatViewOps.ViewOps {

    companion object {
        fun newInstance(): ListChatFragment {
            return ListChatFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_list_chat
    }

    override fun setUp(view: View) {
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ListChatPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }
}