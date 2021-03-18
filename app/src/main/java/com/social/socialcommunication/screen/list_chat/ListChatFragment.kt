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
import com.social.socialcommunication.screen.chat.ChatFragment
import com.social.socialcommunication.screen.in_call.InGoingCallActivity
import com.social.socialcommunication.screen.main.MainActivity
import com.social.socialcommunication.screen.main.MainActivity.Companion.stringeeClient
import com.stringee.StringeeClient
import com.stringee.call.StringeeCall
import com.stringee.exception.StringeeError
import com.stringee.listener.StringeeConnectionListener
import kotlinx.android.synthetic.main.fragment_list_chat.*
import org.json.JSONObject

class ListChatFragment : BaseFragment<ListChatViewOps.PresenterViewOps>(), ListChatViewOps.ViewOps {
    private lateinit var listChatAdapter: ListChatAdapter
    private var userLogin: User? = null

    companion object {
        var callMap = HashMap<String, StringeeCall>()
        fun newInstance(): ListChatFragment {
            return ListChatFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_list_chat
    }

    override fun setUp() {
        setAdapter()
        userLogin = SharedPrefUtils.getInstance(context!!).getAccount()
        mPresenter?.readUserHaveChat(userLogin?.idUser.toString())
    }

    private fun setAdapter() {
        listChatAdapter = ListChatAdapter()
        rcvListChat.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listChatAdapter.setItemClickListener(object : OnItemClickListener<User> {
            override fun itemClickListener(position: Int, value: User) {
                replaceFragment(ChatFragment.newInstance(value), true)
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
                listChatAdapter.clearListUser()
            } else {
                imgEmptyData.visibility = View.GONE
                listChatAdapter.setListUser(listUser)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun initStringee(token: String) {
        try {
            if (stringeeClient == null)
                stringeeClient = StringeeClient(getActivityContext()!!)
            stringeeClient?.setConnectionListener(object : StringeeConnectionListener {
                override fun onTopicMessage(p0: String?, p1: JSONObject?) {
                }

                override fun onRequestNewToken(p0: StringeeClient?) {
                    mPresenter?.getTokenCall(userLogin?.idUser.toString())
                }

                override fun onCustomMessage(p0: String?, p1: JSONObject?) {
                }

                override fun onConnectionConnected(p0: StringeeClient?, p1: Boolean) {
                }

                override fun onConnectionError(p0: StringeeClient?, p1: StringeeError?) {
                }

                override fun onConnectionDisconnected(p0: StringeeClient?, p1: Boolean) {
                }

                override fun onIncomingCall(p0: StringeeCall?) {
                    runOnUIThread(Runnable {
                        kotlin.run {
                            callMap[p0?.d.toString()] = p0!!
                            val userSender = listChatAdapter.getListUser()
                                .filter { user -> user.idUser == p0?.d }[0]
                            startActivity(
                                InGoingCallActivity.newInstance(
                                    getActivityContext()!!,
                                    userSender,
                                    p0?.isVideoCall
                                )
                            )
                        }
                    })
                }

            })
            stringeeClient?.connect(token)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    override fun getListUserFail(mess: String) {
        showToast(mess)
    }

    override fun onGetTokenCall(token: String) {
        initStringee(token)
    }

    override fun onGetTokenCallFail(mess: String) {
        showToast(mess)
    }
}