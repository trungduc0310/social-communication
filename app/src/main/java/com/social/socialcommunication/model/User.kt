package com.social.socialcommunication.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.stfalcon.chatkit.commons.models.IUser

class User : IUser {
    private var avatar: String? = ""

    @SerializedName("nameUser")
    var nameUser: String? = ""

    @SerializedName("idUser")
    var idUser: String? = ""

    @SerializedName("phoneNumber")
    var phoneNumber: String? = ""

    @SerializedName("email")
    var email: String? = ""


    override fun getAvatar(): String {
        return avatar.toString()
    }

    override fun getName(): String {
        return nameUser.toString()
    }

    override fun getId(): String {
        return idUser.toString()
    }

    fun setAvatar(avatarString: String) {
        this.avatar = avatarString
    }

}