package com.social.socialcommunication.model

import com.google.gson.annotations.SerializedName
import com.social.socialcommunication.common.CommonUtils
import com.social.socialcommunication.common.Constant
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

class Messenger : IMessage {
    @SerializedName("id")
    private lateinit var id: String

    @SerializedName("createAt")
    private var createAt: Date? = null

    @SerializedName("userSender")
    private lateinit var user: User

    @SerializedName("userReceiver")
    private lateinit var userReceiver: User

    @SerializedName("contentText")
    private lateinit var text: String

    @SerializedName("imageUrl")
    private lateinit var imageUrl: String


    var isSended = false

    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }


    fun setId(id: String) {
        this.id = id
    }

    override fun getId(): String {
        return id
    }

    override fun getCreatedAt(): Date {
        return createAt!!
    }

    fun setCreateAt(createAt: Date) {
        this.createAt = createAt
    }

    fun setUser(userSender: User) {
        this.user = userSender
    }

    fun getUserReceiver(): User {
        return this.userReceiver
    }

    fun setUserReceiver(userReceiver: User) {
        this.userReceiver = userReceiver
    }

    override fun getUser(): IUser {
        return this.user
    }

    fun setContentText(contentText: String) {
        this.text = contentText
    }

    override fun getText(): String {
        return text
    }

    fun setMessenger(cloneMessenger: CloneMessenger) {
        this.id = cloneMessenger.id
        this.user = cloneMessenger.userSender!!
        this.userReceiver = cloneMessenger.userReceiver!!
        this.createAt = CommonUtils.getDateStringtoDate(cloneMessenger.createAt.toString())
        this.text = cloneMessenger.text
    }

}