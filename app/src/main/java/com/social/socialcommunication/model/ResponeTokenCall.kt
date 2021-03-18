package com.social.socialcommunication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ResponeTokenCall : Serializable {
    @SerializedName("access_token")
    var token: String = ""
}