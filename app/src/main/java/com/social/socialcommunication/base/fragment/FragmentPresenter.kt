package com.social.socialcommunication.base.fragment

import com.social.socialcommunication.base.BasePresenter

class FragmentPresenter<ViewTarget : FragmentViewOps> : BasePresenter<ViewTarget>(),
    FragmentPresenterViewOps {
    override fun onCreate() {
    }

    override fun onPause() {

    }

    override fun onResume() {
    }

    override fun onResumeVisible() {
    }

    override fun onDestroy() {

    }
}