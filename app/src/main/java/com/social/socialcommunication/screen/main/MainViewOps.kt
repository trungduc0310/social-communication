package com.social.socialcommunication.screen.main

import com.social.socialcommunication.base.activity.ActivityPresenterViewOps
import com.social.socialcommunication.base.activity.ActivityViewOps

interface MainViewOps {
    interface ViewOps : ActivityViewOps {

    }

    interface PresenterViewOps : ActivityPresenterViewOps {
    }
}