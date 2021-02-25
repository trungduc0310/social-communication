package com.social.socialcommunication.dialog

import android.content.Context
import android.view.View
import com.social.socialcommunication.R
import com.social.socialcommunication.base.BaseDialog

class PopupMessageDialog : BaseDialog {

    constructor(context: Context) : super(context, android.R.style.Theme_Translucent_NoTitleBar)

    override fun getViewResoure(): Int {
        return R.layout.dialog_message_title_popup
    }

    override fun setUp() {

    }

}