package com.social.socialcommunication.screen.login

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User

class LoginPresenter : FragmentPresenter<LoginViewOps.ViewOps>, LoginViewOps.PresenterViewOps {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var context: Context

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
    }

    override fun loginEmail(email: String, password: String) {
        getView()?.onShowLoading()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                getView()?.onHideLoading()
                if (it.isSuccessful) {
                    val userInfo = mAuth.currentUser
                    val token = FirebaseInstanceId.getInstance().token.toString()
                    var user = User()
                    user.avatar = userInfo?.photoUrl
                    user.email = userInfo?.email
                    user.idUser = userInfo?.uid
                    user.nameUser = userInfo?.displayName
                    user.phoneNumber = userInfo?.phoneNumber
                    SharedPrefUtils.getInstance(context).putAccount(user, token)
                    getView()?.onLoginSuccess()
                } else {
                    getView()?.onLoginFail(context.resources.getString(R.string.text_login_fail))
                }
            }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.onLoginFail(it.message.toString())
            }
    }

    override fun loginGmailWithToken(token: String) {
        getView()?.onShowLoading()
        val credential = GoogleAuthProvider.getCredential(token, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                getView()?.onHideLoading()
                if (it.isSuccessful) {
                    val userInfo = mAuth.currentUser
                    val token = FirebaseInstanceId.getInstance().token.toString()
                    var user = User()
                    user.avatar = userInfo?.photoUrl
                    user.email = userInfo?.email
                    user.idUser = userInfo?.uid
                    user.nameUser = userInfo?.displayName
                    user.phoneNumber = userInfo?.phoneNumber
                    SharedPrefUtils.getInstance(context).putAccount(user, token)
                } else {
                    getView()?.onLoginFail(context.resources.getString(R.string.text_login_fail))
                }
            }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.onLoginFail(it.message.toString())
            }
    }
}