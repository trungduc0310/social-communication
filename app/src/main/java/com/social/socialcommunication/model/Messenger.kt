package com.social.socialcommunication.model

import com.social.socialcommunication.common.CommonUtils
import com.social.socialcommunication.common.Constant
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

class Messenger : IMessage {
    private lateinit var id: String
    private lateinit var createAt: String
    private lateinit var user: User
    private lateinit var contentText: String
    override fun getId(): String {
        return id
    }

    override fun getCreatedAt(): Date {
        return CommonUtils.convertStringToDate(createAt,Constant.APP_DEFAULT_DATE_FORMAT)!!
    }

    override fun getUser(): IUser {
        return user
    }

    override fun getText(): String {
        return contentText
    }
}