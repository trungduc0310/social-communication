package com.social.socialcommunication.screen.register

import android.content.Context
import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.UserProfileChangeRequest
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.model.User

class RegisterPresenter : FragmentPresenter<RegisterViewOps.ViewOps>,
    RegisterViewOps.PresenterViewOps {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var context: Context

    constructor(context: Context) : super() {
        this.context = context
    }


    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
    }

    override fun registerEmail(user: User, password: String) {
        getView()?.onShowLoading()
        val email = user.email.toString()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                getView()?.onHideLoading()
                if (it.isSuccessful) {
                    var userInfo = mAuth.currentUser
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(user.nameUser)
                        .setPhotoUri(user.avatar)
                        .build()
                    userInfo?.updateProfile(profileUpdate)
                    getView()?.onRegisterSuccess()
                } else {
                    getView()?.onRegisterFail(context.getString(R.string.text_register_fail))
                }
            }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.onRegisterFail(it.message.toString())
            }
    }
}