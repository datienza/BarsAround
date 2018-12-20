package com.tide.barsaround.extensions

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.widget.TextView
import androidx.annotation.StyleRes

fun TextView.setTextStyle(style: Int) {
    if (style == Typeface.NORMAL) {
        typeface = Typeface.create(typeface, Typeface.NORMAL)
    } else {
        setTypeface(typeface, style)
    }
}

fun TextView.setStyle(context: Context, @StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(resId)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, resId)
    }
}
