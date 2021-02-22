package com.social.socialcommunication.base.activity

import com.social.socialcommunication.base.BasePresenterOps

public interface ActivityPresenterViewOps : BasePresenterOps {
    fun onPause()
    fun onResume()
}