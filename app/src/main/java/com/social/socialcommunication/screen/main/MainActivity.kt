package com.social.socialcommunication.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.social.socialcommunication.R
import com.social.socialcommunication.base.activity.ActivityPresenter
import com.social.socialcommunication.base.activity.ActivityViewOps
import com.social.socialcommunication.base.activity.BaseActivity

class MainActivity : BaseActivity<MainViewOps.PresenterViewOps>(), MainViewOps.ViewOps {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getViewResoure(): Int {
        TODO("Not yet implemented")
    }

    override fun setUp() {
        TODO("Not yet implemented")
    }

    override fun onRegisterPresenter(): Class<out ActivityPresenter<ActivityViewOps>> {
        TODO("Not yet implemented")
    }
}