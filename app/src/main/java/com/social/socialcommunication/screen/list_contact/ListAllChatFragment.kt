package com.social.socialcommunication.screen.list_contact

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.OnItemClickListener
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User
import com.social.socialcommunication.screen.chat.ChatFragment
import kotlinx.android.synthetic.main.fragment_list_contact.*
import java.lang.Exception

class ListAllChatFragment : BaseFragment<ListContactViewOps.PresenterViewOps>(),
    ListContactViewOps.ViewOps {
    companion object {
        fun newInstance(): ListAllChatFragment {
            return ListAllChatFragment()
        }
    }

    private lateinit var listAllChatAdapter: ListAllChatAdapter

    override fun getViewResoure(): Int {
        return R.layout.fragment_list_contact
    }

    override fun setUp() {
        setAdapter()
        val userLogin = SharedPrefUtils.getInstance(context!!).getAccount()
        mPresenter?.readUser(userLogin?.idUser.toString())
    }

    private fun setAdapter() {
        rcvListContact.layoutManager =
            LinearLayoutManager(getActivityContext()!!, LinearLayoutManager.VERTICAL, false)
        listAllChatAdapter = ListAllChatAdapter()
        listAllChatAdapter.setItemClickListener(object : OnItemClickListener<User> {
            override fun itemClickListener(position: Int, value: User) {
                replaceFragment(ChatFragment.newInstance(value), true)
            }

        })
        rcvListContact.adapter = listAllChatAdapter
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ListContactPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun getListUserSuccess(listUser: ArrayList<User>) {
        try {
            if (listUser.isEmpty()) {
                imgEmptyData.visibility = View.VISIBLE
            } else {
                imgEmptyData.visibility = View.GONE
                listAllChatAdapter.setListUser(listUser)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun getListUserFail(mess: String) {
        showToast(mess)
    }
}