package com.social.socialcommunication.screen.list_chat

import com.google.firebase.database.*
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.Messenger
import com.social.socialcommunication.model.ResponeTokenCall
import com.social.socialcommunication.model.User
import com.social.socialcommunication.service.ApiConnect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListChatPresenter : FragmentPresenter<ListChatViewOps.ViewOps>(),
    ListChatViewOps.PresenterViewOps {
    private lateinit var mReferenceChat: DatabaseReference
    private lateinit var mReference: DatabaseReference
    override fun onCreate() {
        super.onCreate()
    }

    override fun readUserHaveChat(idUserLogin: String) {
        getView()?.showProgressbar()
        mReferenceChat = FirebaseDatabase.getInstance().getReference("Chats")
        var userList: ArrayList<User> = ArrayList()
        mReferenceChat.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                getView()?.hideProgressbar()
                getView()?.getListUserFail(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (chil in snapshot.children) {
                    val cloneMessenger = chil.getValue(CloneMessenger::class.java)
                    val messenger = Messenger()
                    if (cloneMessenger != null) {
                        messenger.setMessenger(cloneMessenger!!)
                        if (messenger?.user.id == idUserLogin) {
                            userList.add(messenger.getUserReceiver())
                        }
                        if (messenger?.getUserReceiver().idUser == idUserLogin) {
                            userList.add(messenger?.user as User)
                        }
                    }
                }

                readChat(userList, idUserLogin)
            }

        })
    }

    override fun readChat(userList: ArrayList<User>, idUserLogin: String) {
        var mUsers = ArrayList<User>()
        mReference = FirebaseDatabase.getInstance().getReference("Users")
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                getView()?.hideProgressbar()
                getView()?.getListUserFail(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                getView()?.hideProgressbar()
                mUsers.clear()
                try {
                    for (data in snapshot.children) {
                        val userSnap = data.getValue(User::class.java)
                        for (user in userList) {
                            if (user.idUser.equals(userSnap?.idUser)) {
                                if (mUsers.size != 0) {
                                    for (i in 0 until mUsers.size) {
                                        val user1 = mUsers[i]
                                        if (!user1.idUser.equals(user.idUser) &&
                                            mUsers.filter { user1 -> user1.idUser.equals(user.idUser) }.size != 1
                                        ) {
                                            mUsers.add(userSnap!!)
                                            break
                                        }
                                    }
                                } else {
                                    mUsers.add(userSnap!!)
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                getView()?.getListUserSuccess(mUsers)
                getTokenCall(idUserLogin)
            }

        })
    }

    override fun getTokenCall(userId: String) {
        ApiConnect.builderV1()?.getTokenCall(userId)?.enqueue(object : Callback<ResponeTokenCall> {
            override fun onFailure(call: Call<ResponeTokenCall>, t: Throwable) {
                getView()?.onGetTokenCallFail(t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponeTokenCall>,
                response: Response<ResponeTokenCall>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val respone = response.body()!!.token
                    getView()?.onGetTokenCall(respone)
                } else {
                    getView()?.onGetTokenCallFail(response.message())
                }
            }

        })
    }
}