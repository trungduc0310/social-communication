package com.social.socialcommunication.screen.login

import android.content.Intent
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.dialog.OnButtonClickListener
import com.social.socialcommunication.screen.forgot_password.ForgotPasswordDialog
import com.social.socialcommunication.screen.main.MainActivity
import com.social.socialcommunication.screen.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewOps.PresenterViewOps>(), LoginViewOps.ViewOps,
    View.OnClickListener {
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

    override fun setUp() {
        setEventClick()
        initGoogleAPI()
    }

    private fun setEventClick() {
        btnLoginGmail.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        btnToViewRegister.setOnClickListener(this)
        txtForgotPassword.setOnClickListener(this)
    }

    private fun initGoogleAPI() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.value_id_token))
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

    private fun handleSignIn(task: Task<GoogleSignInAccount>?) {
        try {
            if (task != null) {
                val acct = task.getResult(ApiException::class.java)
                val token = acct?.idToken.toString()
                mPresenter?.loginGmailWithToken(getActivityContext()!!, token)
            }
        } catch (ex: ApiException) {
            Log.d("signInResult:failed", ex.message.toString())
        }

    }

    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return LoginPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onLoginSuccess() {
        startActivity(MainActivity.newInstance(activity!!))
        getActivityReference()?.finish()
        mGoogleSignInClient.signOut()
    }

    override fun onLoginFail(mess: String) {
        showToast(mess)
    }

    override fun onShowLoading() {
        showProgressbar()
    }

    override fun onHideLoading() {
        hideProgressbar()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnLoginGmail -> {
                loginGmail()
            }
            R.id.btnLogin -> {
                val email = edtInputEmail.text.toString()
                val password = edtInputPassword.text.toString()
                if (loginEmail(email, password)) {
                    mPresenter?.loginEmail(getActivityContext()!!, email, password)
                }
            }
            R.id.btnToViewRegister -> {
                replaceFragment(RegisterFragment.newInstance(), true)
            }
            R.id.txtForgotPassword -> {
                val forgotDialog = ForgotPasswordDialog(getActivityContext()!!)
                forgotDialog.setButtonClickListener(object : OnButtonClickListener<String> {
                    override fun onAcceptClickListener(value: String) {
                        mPresenter?.forgotPassword(value)
                    }

                    override fun onCancelClickListener() {
                    }

                })
                forgotDialog.show()
            }
        }
    }

    private fun loginEmail(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            showToast(getString(R.string.text_email_empty))
            return false
        }
        if (password.isEmpty()) {
            showToast(getString(R.string.text_password_empty))
            return false
        }
        return true
    }

}