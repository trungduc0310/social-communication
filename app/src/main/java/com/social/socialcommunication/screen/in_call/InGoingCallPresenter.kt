package com.social.socialcommunication.screen.in_call

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.social.socialcommunication.base.BasePresenter
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.Messenger

class InGoingCallPresenter : ActivityPresenter<InGoingCallViewOps.ViewOps>(),
    InGoingCallViewOps.PresenterViewOps {
    private lateinit var mReferenceChat: DatabaseReference

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
        }.addOnFailureListener {  }
    }
}