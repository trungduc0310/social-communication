package com.social.socialcommunication.dialog

import android.content.Context
import android.view.View
import android.widget.Toast
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_change_name.*

class ChangeNameDialog : BaseDialog, View.OnClickListener {
    constructor(name: String, context: Context) : super(
        context,
        android.R.style.Theme_Translucent_NoTitleBar
    ) {
        this.name = name
    }

    private var name: String = ""
    private var onButtonClickListener: OnButtonClickListener<String>? = null

    fun setOnButtonClickListener(onButtonClickListener: OnButtonClickListener<String>) {
        this.onButtonClickListener = onButtonClickListener
    }

    override fun getViewResoure(): Int {
        return R.layout.dialog_change_name
    }

    override fun setUp() {
        setDataOnView()
        setEventClick()
    }

    private fun setEventClick() {
        btnCancel.setOnClickListener(this)
        btnAccept.setOnClickListener(this)
    }

    private fun setDataOnView() {
        tvInputChangeName.setText(name)
        tvInputChangeName.selectAll()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnCancel -> {
                dismiss()
            }
            R.id.btnAccept -> {
                val newName = tvInputChangeName.text.toString()
                if (newName.isNotEmpty()) {
                    onButtonClickListener?.onAcceptClickListener(newName)
                    dismiss()
                } else {
                    dismiss()
                }

            }
        }
    }
}