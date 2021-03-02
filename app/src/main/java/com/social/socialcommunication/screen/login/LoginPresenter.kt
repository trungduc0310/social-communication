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

    constructor()


    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
    }

    override fun loginEmail(context: Context, email: String, password: String) {
        getView()?.onShowLoading()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                getView()?.onHideLoading()
                if (it.isSuccessful) {
                    val userInfo = mAuth.currentUser
                    var user = User()
                    val avatar = userInfo?.photoUrl
                    user.email = userInfo?.email
                    user.idUser = userInfo?.uid
                    user.nameUser = userInfo?.displayName
                    user.phoneNumber = userInfo?.phoneNumber
                    userInfo?.getIdToken(true)?.addOnCompleteListener {
                        val token = it.result?.token.toString()
                        SharedPrefUtils.getInstance(context).putAccount(user, token, avatar)
                        getView()?.onLoginSuccess()
                    }
                        ?.addOnFailureListener {
                            getView()?.onLoginFail(context.resources.getString(R.string.text_token_fail))
                        }
                } else {
                    getView()?.onLoginFail(context.resources.getString(R.string.text_login_fail))
                }
            }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.onLoginFail(it.message.toString())
            }
    }

    override fun loginGmailWithToken(context: Context, token: String) {
        getView()?.onShowLoading()
        val credential = GoogleAuthProvider.getCredential(token, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                getView()?.onHideLoading()
                if (it.isSuccessful) {
                    val userInfo = mAuth.currentUser
                    var user = User()
                    val avatar = userInfo?.photoUrl
                    user.email = userInfo?.email
                    user.idUser = userInfo?.uid
                    user.nameUser = userInfo?.displayName
                    user.phoneNumber = userInfo?.phoneNumber
                    SharedPrefUtils.getInstance(context).putAccount(user, token, avatar)
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
}