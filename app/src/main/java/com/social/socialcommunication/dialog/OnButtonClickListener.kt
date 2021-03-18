package com.social.socialcommunication.dialog

interface OnButtonClickListener<T> {
    fun onAcceptClickListener(value: T)
    fun onCancelClickListener()
}