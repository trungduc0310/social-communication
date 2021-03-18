package com.social.socialcommunication.model

import com.stfalcon.chatkit.commons.models.IUser
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.util.*

class MessengerImage : MessageContentType.Image {

    override fun getImageUrl(): String? {
        TODO("Not yet implemented")
    }

    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getCreatedAt(): Date {
        TODO("Not yet implemented")
    }

    override fun getUser(): IUser {
        TODO("Not yet implemented")
    }

    override fun getText(): String {
        TODO("Not yet implemented")
    }
}