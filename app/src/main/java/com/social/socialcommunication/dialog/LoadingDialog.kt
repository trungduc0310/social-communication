package com.social.socialcommunication.dialog

import android.content.Context
import android.os.Handler
import android.view.View
import androidx.fragment.app.FragmentManager
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog

class LoadingDialog : BaseDialog {
    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    companion object {
        private const val DELAY = 10000
    }


    private var mHandler = Handler()
    private val runnable = Runnable {
        kotlin.run {
            try {
                if (isShowing) {
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

    override fun setUp() {
        setCancelable(false)
    }

    override fun show() {
        super.show()
        if (runnable != null && mHandler != null) {
            mHandler.removeCallbacks(runnable)
            mHandler.postDelayed(runnable, DELAY.toLong())
        }
    }


}