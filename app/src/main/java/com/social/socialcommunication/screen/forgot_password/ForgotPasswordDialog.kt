package com.social.socialcommunication.screen.forgot_password

import android.content.Context
import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_forgot_password.*

class ForgotPasswordDialog : BaseDialog, View.OnClickListener {

    constructor(context: Context) : super(context, android.R.style.Theme_Translucent_NoTitleBar)

    override fun getViewResoure(): Int {
        return R.layout.dialog_forgot_password
    }

    override fun setUp() {
        setEventClick()
    }

    private fun setEventClick() {
        btnAccept.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnCancel -> {
                dismiss()
            }
            R.id.btnAccept -> {
            }
        }
    }
}