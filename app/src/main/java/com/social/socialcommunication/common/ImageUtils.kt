package com.social.socialcommunication.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.social.socialcommunication.R

class ImageUtils {
    companion object {
        fun loadImage(context: Context, imageView: ImageView, url: Any) {
            Glide.with(context).load(url)
                .error(R.drawable.ic_info_gray)
                .placeholder(R.color.gray1)
                .into(imageView)
        }
    }
}