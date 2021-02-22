package com.social.socialcommunication.base.fragment

import com.social.socialcommunication.base.BasePresenterOps
import com.social.socialcommunication.base.PresenterViewOps

public interface FragmentPresenterViewOps : BasePresenterOps {
    fun onPause()
    fun onResume()
    fun onResumeVisible()
}