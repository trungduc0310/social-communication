package com.social.socialcommunication.service

import com.social.socialcommunication.model.ResponeTokenCall
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRestService {
    @GET("access-token.php")
    fun getTokenCall(@Query("user-id") userId: String): Call<ResponeTokenCall>
}