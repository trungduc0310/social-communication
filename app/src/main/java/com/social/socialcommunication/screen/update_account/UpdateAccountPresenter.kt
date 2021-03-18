package com.social.socialcommunication.screen.update_account

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User
import java.util.concurrent.TimeUnit

class UpdateAccountPresenter : FragmentPresenter<UpdateAccountViewOps.ViewOsp>(),
    UpdateAccountViewOps.PersenterViewOps {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
    }

    override fun updateProfile(context: Context, user: User) {
        getView()?.showProgressbar()
        val mUser = mAuth.currentUser
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(user.nameUser)
            .setPhotoUri(Uri.parse(user.avatar))
            .build()
        mUser?.updateProfile(profileUpdate)
            ?.addOnCompleteListener {
                getView()?.hideProgressbar()
                if (it.isSuccessful) {
                    SharedPrefUtils.getInstance(context).putAccount(user, Uri.parse(user.avatar))
                    getView()?.onUpdateSuccess()
                } else {
                    getView()?.onUpdateFail("Không cập nhật được tài khoản")
                }
            }
            ?.addOnFailureListener {
                getView()?.onUpdateFail(it.message.toString())
            }
    }

    override fun updatePassword(newPassword: String) {
        getView()?.showProgressbar()
        val mUser = mAuth.currentUser
        mUser?.updatePassword(newPassword)
            ?.addOnCompleteListener {
                getView()?.hideProgressbar()
                if (it.isSuccessful) {
                    getView()?.onUpdateSuccess()
                } else {
                    getView()?.onUpdateFail("Mật khẩu cũ không đúng")
                }
            }
            ?.addOnFailureListener {
                getView()?.hideProgressbar()
                getView()?.onUpdateFail(it.message.toString())
            }
    }

    override fun updatePhoneNumber(activity: Activity, phoneNumber: String) {
        val mUser = mAuth.currentUser
        mAuth.setLanguageCode("vi")
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    getView()?.hideProgressbar()
                    mUser?.updatePhoneNumber(p0)
                    getView()?.onUpdateSuccess()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    getView()?.hideProgressbar()
                    if (p0 is FirebaseAuthInvalidCredentialsException) {
                        getView()?.onUpdateFail("Yêu cầu không hợp lệ")
                    } else if (p0 is FirebaseTooManyRequestsException) {
                        getView()?.onUpdateFail("Quá hạn mức gửi tin nhắn ")
                    }
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    Log.d("onCodeSent:", p0)
                }

            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        getView()?.showProgressbar()
    }


}