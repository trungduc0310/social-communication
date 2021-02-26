package com.social.socialcommunication.model

import com.stfalcon.chatkit.commons.models.IUser

class User : IUser {
    private lateinit var avatar: String
    private lateinit var nameUser: String
    private lateinit var id: String
    override fun getAvatar(): String {
        return avatar
    }

    override fun getName(): String {
        return nameUser
    }

    override fun getId(): String {
        return id
    }
}