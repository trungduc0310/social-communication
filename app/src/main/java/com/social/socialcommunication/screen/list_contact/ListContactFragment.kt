package com.social.socialcommunication.screen.list_contact

import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps

class ListContactFragment : BaseFragment<ListContactViewOps.PresenterViewOps>(),
    ListContactViewOps.ViewOps {
    companion object {
        fun newInstance(): ListContactFragment {
            return ListContactFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_list_contact
    }

    override fun setUp() {
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ListContactPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }
}