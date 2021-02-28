package com.social.socialcommunication.screen.login

import android.content.Intent
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.model.User

class LoginFragment : BaseFragment<LoginViewOps.PresenterViewOps>(), LoginViewOps.ViewOps {
    private val RC_SIGN_IN = 10000
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        fun getInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_login
    }

    override fun setUp(view: View) {
        initGoogleAPI()
    }

    private fun initGoogleAPI() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(getActivityReference()!!, gso)
    }

    private fun loginGmail() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignIn(task)
        }
    }

    private fun handleSignIn(task: Task<GoogleSignInAccount>) {
        try {
            if (task != null) {
                val acct = task.getResult(ApiException::class.java)
                val token = acct?.idToken.toString()
                mPresenter?.loginGmailWithToken(token)
            }
        } catch (ex: ApiException) {
            Log.d("signInResult:failed", ex.message.toString())
        }
    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return LoginPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onLoginSuccess() {

    }

    override fun onLoginFail(mess: String) {

    }

    override fun onShowLoading() {
        showProgressbar()
    }

    override fun onHideLoading() {
        hideProgressbar()
    }

}