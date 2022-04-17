package com.example.demo.util

import android.content.res.Resources.getSystem

object DisplayUtils {
    val Int.pxToDp: Float get() = (this / getSystem().displayMetrics.density)
    val Int.dpToPx: Float get() = (this * getSystem().displayMetrics.density)

    fun statusBarHeight(): Int {
        val resourceId = getSystem().getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
        return if (resourceId > 0) {
            getSystem().getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }
}

