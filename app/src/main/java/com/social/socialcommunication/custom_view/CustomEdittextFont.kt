package com.social.socialcommunication.custom_view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import com.social.socialcommunication.R


class CustomEdittextFont : androidx.appcompat.widget.AppCompatEditText {
    companion object {
        var currentColor = Color.BLACK
    }

    private var typeFont = "quicksand/quicksand_medium.ttf"

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        currentColor = currentTextColor
        try {
            val a = context?.obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextviewFont, 0, 0
            )
            typeFont = a.getString(R.styleable.CustomTextviewFont_font_type).toString()
            if (typeFont == null) {
                typeFont = resources.getString(R.string.typeface_regular)
            }
            typeface = FontCache.getTypeFace(context, typeFont)
            a.recycle()
        } catch (ex: Exception) {
            typeface = try {
                Typeface.createFromAsset(
                    context?.assets,
                    resources.getString(R.string.typeface_regular)
                )
            } catch (ex: Exception) {
                Typeface.DEFAULT
            }
        }
    }

}