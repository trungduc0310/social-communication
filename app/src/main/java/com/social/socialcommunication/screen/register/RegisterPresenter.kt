package com.social.socialcommunication.screen.register

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.model.User

class RegisterPresenter : FragmentPresenter<RegisterViewOps.ViewOps>,
    RegisterViewOps.PresenterViewOps {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mReference: DatabaseReference

    constructor()

    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance()
    }

    override fun registerEmail(context: Context, user: User, password: String) {
        getView()?.onShowLoading()
        val email = user.email.toString()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                getView()?.onHideLoading()
                if (it.isSuccessful) {
                    var userInfo = mAuth.currentUser
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(user.nameUser)
                        .setPhotoUri(Uri.parse(user.avatar))
                        .build()
                    userInfo?.updateProfile(profileUpdate)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            addMember(context, userInfo)
                        } else {
                            getView()?.onRegisterFail(context.getString(R.string.text_register_fail))
                        }
                    }
                        ?.addOnFailureListener {
                            getView()?.onRegisterFail(it.message.toString())
                        }
                } else {
                    getView()?.onRegisterFail(context.getString(R.string.text_register_fail))
                }
            }
            .addOnFailureListener {
                getView()?.onHideLoading()
                getView()?.onRegisterFail(it.message.toString())
            }
    }

    override fun addMember(context: Context, user: FirebaseUser) {
        mReference = FirebaseDatabase.getInstance().getReference("Users").child(user.uid)
        var infoUser = HashMap<String, String>()
        infoUser["idUser"] = user.uid
        infoUser["email"] = user.email.toString()
        infoUser["nameUser"] = user.displayName.toString()
        infoUser["avatar"] = user.photoUrl.toString()
        infoUser["phoneNumber"] = user.phoneNumber.toString()
        mReference.setValue(infoUser).addOnCompleteListener {
            if (it.isSuccessful) {
                getView()?.onRegisterSuccess()
            } else {
                getView()?.onRegisterFail(context.getString(R.string.text_register_fail))
            }
        }
            .addOnFailureListener {
                getView()?.onRegisterFail(it.message.toString())
            }

    }
}