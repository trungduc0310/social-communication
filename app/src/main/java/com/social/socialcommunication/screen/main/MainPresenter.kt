package com.social.socialcommunication.screen.main

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.base.activity.ActivityPresenterViewOps
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.common.Constant
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.ResponeTokenCall
import com.social.socialcommunication.model.User
import com.social.socialcommunication.service.ApiConnect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter : ActivityPresenter<MainViewOps.ViewOps>(), MainViewOps.PresenterViewOps {


}