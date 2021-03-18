package com.social.socialcommunication.screen.chat

import com.social.socialcommunication.base.fragment.FragmentPresenterViewOps
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.model.CloneMessenger
import com.social.socialcommunication.model.Messenger

interface ChatViewOps {
    interface ViewOps : FragmentViewOps {
        fun onSendMess(messenger: Messenger)
        fun onSendedMess(messenger: Messenger)
        fun onSendFail(mess: String)
        fun onReadListchatSuccess(listChat: ArrayList<Messenger>)

    }

    interface PresenterViewOps : FragmentPresenterViewOps {
        fun sendMessages(messenger: CloneMessenger)
        fun addMessagess(idSender: String, idReceiver: String)
    }
}