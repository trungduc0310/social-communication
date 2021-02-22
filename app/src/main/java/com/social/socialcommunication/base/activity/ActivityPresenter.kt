package com.social.socialcommunication.base.activity

import com.social.socialcommunication.base.BasePresenter

public abstract class ActivityPresenter<ViewTarget : ActivityViewOps> : BasePresenter<ViewTarget>(),
    ActivityPresenterViewOps {
    override fun onCreate() {
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDestroy() {

    }
}