package com.social.socialcommunication.screen.register

import android.net.Uri
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.social.socialcommunication.R
import com.social.socialcommunication.base.fragment.BaseFragment
import com.social.socialcommunication.base.fragment.FragmentPresenter
import com.social.socialcommunication.base.fragment.FragmentViewOps
import com.social.socialcommunication.model.User
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment<RegisterViewOps.PresenterViewOps>(), RegisterViewOps.ViewOps,
    View.OnClickListener {

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.fragment_register
    }

    override fun setUp() {
        setEventClick()
    }

    private fun setEventClick() {
        btnBack.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }


    override fun onRegisterPresenter(): Class<out FragmentPresenter<FragmentViewOps>> {
        return RegisterPresenter::class.java as Class<out FragmentPresenter<FragmentViewOps>>
    }

    override fun onShowLoading() {
        showProgressbar()
    }

    override fun onHideLoading() {
        hideProgressbar()
    }

    override fun onRegisterSuccess() {
        getActivityReference()?.onBackPressed()
    }

    override fun onRegisterFail(mess: String) {
        showToast(mess)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnBack -> {
                getActivityReference()?.onBackPressed()
            }
            R.id.btnRegister -> {
                val assetPath = "file:///android_asset/user_default.png"
                val userAvt = Uri.parse(assetPath)
                val userName = edtInputFullName.text.toString()
                val email = edtInputEmail.text.toString()
                val password = edtInputPassword.text.toString()
                if (registerUser(userName, email, password)) {
                    val userInfo = User()
                    userInfo.avatar = userAvt.toString()
                    userInfo.email = email
                    userInfo.nameUser = userName
                    mPresenter?.registerEmail(getActivityContext()!!, userInfo, password)
                }

            }
        }
    }

    private fun registerUser(userName: String, email: String, password: String): Boolean {
        if (userName.isEmpty()) {
            showToast(getString(R.string.text_user_name_empty))
            return false
        }
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