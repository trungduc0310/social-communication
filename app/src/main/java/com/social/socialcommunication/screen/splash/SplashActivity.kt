package com.social.socialcommunication.screen.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.social.socialcommunication.R
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.base.activity.BaseActivity
import com.social.socialcommunication.common.SharedPrefUtils
import com.social.socialcommunication.screen.login.LoginFragment
import com.social.socialcommunication.screen.main.MainActivity

class SplashActivity : BaseActivity<SplashViewOps.PresenterViewOps>(), SplashViewOps.ViewOps {
    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewResoure(): Int {
        return R.layout.activity_splash
    }

    override fun setUp() {
        Handler().postDelayed(Runnable {
            kotlin.run {
                if (SharedPrefUtils.getInstance(this).isLogin()) {
                    startActivity(MainActivity.newInstance(this))
                    finish()
                } else {
                    replaceFragment(LoginFragment.getInstance(), android.R.id.content, true)
                }
            }
        }, 2000)
    }

    override fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>> {
        return SplashPresenter::class.java as Class<out ActivityPresenter<ActivityViewOps>>
    }

    override fun onBackPressed() {
        if (checkBackPressedScreen()) {
            return
        }
        super.onBackPressed()
    }

    private fun checkBackPressedScreen(): Boolean {
        val fg = supportFragmentManager.findFragmentById(android.R.id.content)
        if (fg is LoginFragment) {
            finish()
            return true
        }
        return false
    }
}