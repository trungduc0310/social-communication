package com.social.socialcommunication.dialog

import android.content.Context
import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_change_avatar.*

class ChangeAvatarDialog : BaseDialog, View.OnClickListener {
    constructor(context: Context) : super(
        context,
        android.R.style.Theme_Translucent_NoTitleBar
    )

    private var onButtonClickListener: OnTakeImageListener? = null

    fun setOnButtonClickListener(onButtonClickListener: OnTakeImageListener) {
        this.onButtonClickListener = onButtonClickListener
    }

    override fun getViewResoure(): Int {
        return R.layout.dialog_change_avatar
    }

    override fun setUp() {
        setEventClick()
    }

    private fun setEventClick() {
        btnTakeCamera.setOnClickListener(this)
        btnTakeImage.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnTakeCamera -> {
                onButtonClickListener?.onOpenCamera()
                dismiss()
            }
            R.id.btnTakeImage -> {
                onButtonClickListener?.onOpenGalery()
                dismiss()
            }
        }
    }

    interface OnTakeImageListener {
        fun onOpenGalery()
        fun onOpenCamera()
    }
}