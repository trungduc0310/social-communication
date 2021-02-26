package com.social.socialcommunication.screen.splash

import android.os.Bundle
import com.social.socialcommunication.R
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.base.activity.BaseActivity

class SplashActivity : BaseActivity<SplashViewOps.PresenterViewOps>(), SplashViewOps.ViewOps {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewResoure(): Int {
        return R.layout.activity_splash
    }

    override fun setUp() {
    }

    override fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>> {
        return SplashPresenter::class.java as Class<out ActivityPresenter<ActivityViewOps>>
    }
}