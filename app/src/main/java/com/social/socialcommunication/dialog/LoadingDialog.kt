package com.social.socialcommunication.dialog

import android.os.Handler
import android.view.View
import androidx.fragment.app.FragmentManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog

class LoadingDialog : BaseDialog() {
    companion object {
        private const val DELAY = 10000
    }

    private var mHandler = Handler()
    private val runnable = Runnable {
        kotlin.run {
            try {
                if (isVisible) {
                    dismiss()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun getViewResoure(): Int {
        return R.layout.dialog_loading
    }

    override fun setUp(view: View) {
        isCancelable = false
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        if (runnable != null && mHandler != null) {
            mHandler.removeCallbacks(runnable)
            mHandler.postDelayed(runnable, DELAY.toLong())
        }
    }

}