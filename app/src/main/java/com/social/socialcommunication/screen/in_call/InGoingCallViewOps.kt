package com.social.socialcommunication.screen.in_call

import com.social.socialcommunication.base.activity.ActivityPresenterViewOps
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.model.CloneMessenger

interface InGoingCallViewOps {
    interface ViewOps : ActivityViewOps {

    }

    interface PresenterViewOps : ActivityPresenterViewOps{
        fun sendMessages(messenger: CloneMessenger)
    }
}