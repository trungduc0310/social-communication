package com.social.socialcommunication.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import com.social.socialcommunication.common.CommonUtils

public abstract class BaseDialog : Dialog {
    private lateinit var mContext: Context

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        mContext = context
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window!!.statusBarColor = Color.parseColor("#EBF0F7")
        }
        window!!.setWindowAnimations(themeResId)
        val view = View.inflate(mContext, getViewResoure(), null)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    abstract fun getViewResoure(): Int
    abstract fun setUp()
}