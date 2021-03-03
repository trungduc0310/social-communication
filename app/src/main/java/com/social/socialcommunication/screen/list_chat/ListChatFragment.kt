package com.social.socialcommunication.screen.list_chat

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.OnItemClickListener
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User
import kotlinx.android.synthetic.main.fragment_list_chat.*

class ListChatFragment : BaseFragment<ListChatViewOps.PresenterViewOps>(), ListChatViewOps.ViewOps {
    private lateinit var listChatAdapter: ListChatAdapter

    companion object {
        fun newInstance(): ListChatFragment {
            return ListChatFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_list_chat
    }

    override fun setUp() {
        setAdapter()
        val userLogin = SharedPrefUtils.getInstance(context!!).getAccount()
        mPresenter?.readUser(userLogin?.idUser.toString())
    }

    private fun setAdapter() {
        listChatAdapter = ListChatAdapter()
        rcvListChat.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listChatAdapter.setItemClickListener(object : OnItemClickListener<User> {
            override fun itemClickListener(position: Int, value: User) {

            }
        })
        rcvListChat.adapter = listChatAdapter
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return ListChatPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun getListUserSuccess(listUser: ArrayList<User>) {
        try {
            if (listUser.isEmpty()) {
                imgEmptyData.visibility = View.VISIBLE
            } else {
                imgEmptyData.visibility = View.GONE
                listChatAdapter.setListUser(listUser)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun getListUserFail(mess: String) {
        showToast(mess)
    }
}