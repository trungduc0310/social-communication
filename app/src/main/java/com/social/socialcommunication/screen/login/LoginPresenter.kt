package com.social.socialcommunication.screen.login

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User

class LoginPresenter : FragmentPresenter<LoginViewOps.ViewOps>(), LoginViewOps.PresenterViewOps {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mReference: DatabaseReference
    private lateinit var mReference2: DatabaseReference


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
                    user.avatar = avatar.toString()
                    SharedPrefUtils.getInstance(context).putAccount(user, token, avatar)
                    getView()?.onLoginSuccess()
                    addMember(user)
                } else {
                    getView()?.onLoginFail(context.resources.getString(R.string.text_login_fail))
                }
            }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.onLoginFail(it.message.toString())
            }
    }

    override fun forgotPassword(email: String) {
        getView()?.onShowLoading()
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            getView()?.onHideLoading()
            if (it.isSuccessful){
                getView()?.showToast("Chúng tôi đã gửi yêu cầu tới $email vui lòng kiểm tra hộp thư của bạn.")
            }else{
                getView()?.showToast("Yêu cầu thất bại")
            }
        }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.showToast(it.message.toString())
            }
    }

    private fun addMember(user: User) {
        mReference = FirebaseDatabase.getInstance().getReference("Users")
        mReference2 = FirebaseDatabase.getInstance().getReference("Users").child(user.id)
        val userId = user.idUser
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0
                for (chil in snapshot.children) {
                    val user = chil.getValue(User::class.java)
                    if (userId.equals(user?.idUser)) {
                        count++
                    }
                }
                if (count == 0) {
                    var infoUser = HashMap<String, String>()
                    infoUser["idUser"] = user.id
                    infoUser["email"] = user.email.toString()
                    infoUser["nameUser"] = user.nameUser.toString()
                    infoUser["avatar"] = user.avatar
                    infoUser["phoneNumber"] = user.phoneNumber.toString()
                    mReference2.setValue(infoUser)
                }
            }

        })
    }


}