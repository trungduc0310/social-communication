package com.social.socialcommunication.base

interface OnItemClickListener<T> {
    fun itemClickListener(position: Int, value: T)
}