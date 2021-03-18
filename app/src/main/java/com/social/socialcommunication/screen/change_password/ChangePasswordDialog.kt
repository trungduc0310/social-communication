package com.social.socialcommunication.screen.change_password

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog
import com.social.socialcommunication.dialog.OnButtonClickListener
import kotlinx.android.synthetic.main.dialog_change_password.*

class ChangePasswordDialog : BaseDialog, View.OnClickListener, TextWatcher {
    private var onButtonClickListener: OnButtonClickListener<String>? = null

    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener<String>) {
        this.onButtonClickListener = onButtonClickListener
    }

    constructor(context: Context) : super(
        context,
        android.R.style.Theme_Translucent_NoTitleBar
    )

    override fun getViewResoure(): Int {
        return R.layout.dialog_change_password
    }

    override fun setUp() {
        setEventClick()
    }

    private fun setEventClick() {
        btnAccept.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        tvInputReNewPass.addTextChangedListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnAccept -> {
                val newPassword = tvInputReNewPass.text.toString()
                onButtonClickListener?.onAcceptClickListener(newPassword)
                dismiss()
            }
            R.id.btnCancel -> {
                onButtonClickListener?.onCancelClickListener()
                dismiss()
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {
        if (p0?.toString().equals(tvInputNewPass.text.toString())!!) {
            btnAccept.isEnabled = true
            imgCheckRenew.setImageResource(R.drawable.ic_check_circle_green)
        } else {
            btnAccept.isEnabled = false
            imgCheckRenew.setImageResource(R.drawable.ic_info_red)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}