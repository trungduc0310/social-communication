package com.social.socialcommunication.screen.chat

import com.google.firebase.database.*
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.Messenger
import com.social.socialcommunication.model.ResponeTokenCall
import com.social.socialcommunication.service.ApiConnect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatPresenter : FragmentPresenter<ChatViewOps.ViewOps>(), ChatViewOps.PresenterViewOps {
    private lateinit var mReferenceChat: DatabaseReference
    private lateinit var referenceChat: DatabaseReference
    override fun onCreate() {
        super.onCreate()
        mReferenceChat = FirebaseDatabase.getInstance().reference
    }

    override fun sendMessages(messengerClone: CloneMessenger) {
        val messenger = Messenger()
        messenger.setMessenger(messengerClone)
        var hashMap = HashMap<String, Any>()
        hashMap["id"] = messengerClone.id
        hashMap["userReceiver"] = messengerClone.userReceiver!!
        hashMap["userSender"] = messengerClone.userSender!!
        hashMap["text"] = messengerClone.text
        hashMap["createAt"] = messengerClone.createAt.toString()
        mReferenceChat.child("Chats").push().setValue(hashMap).addOnCompleteListener {
            getView()?.onSendedMess(messenger)
        }.addOnFailureListener { getView()?.onSendFail(it.message.toString()) }

    }

    override fun addMessagess(idSender: String, idReceiver: String) {
        referenceChat = FirebaseDatabase.getInstance().getReference("Chats")
        referenceChat.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val cloneMessenger = snapshot.getValue(CloneMessenger::class.java)
                if (cloneMessenger?.userReceiver?.id == idReceiver && cloneMessenger?.userSender?.id == idSender ||
                    cloneMessenger?.userReceiver?.id == idSender && cloneMessenger?.userSender?.id == idReceiver
                ) {
                    val messenger = Messenger()
                    messenger.setMessenger(cloneMessenger!!)
                    getView()?.onSendMess(messenger)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

        })
    }




}