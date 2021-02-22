package com.social.socialcommunication.base

import java.lang.ref.WeakReference

public abstract class BasePresenter<ViewTarget : BaseViewOps> : BasePresenterOps, PresenterViewOps {
    private var targetWeakReference: WeakReference<ViewTarget>? = null
    protected var isRelease = false

    open fun takeView(viewTarget: BaseViewOps) {
        targetWeakReference = WeakReference(viewTarget as ViewTarget)
    }

    protected open fun getView(): ViewTarget? {
        return targetWeakReference!!.get()
    }

    override fun onDestroy() {
        targetWeakReference!!.clear()
        targetWeakReference = null
        isRelease = true
    }

    override fun onRelease() {
        isRelease = true
        targetWeakReference!!.clear()
    }
}