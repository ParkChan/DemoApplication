package com.example.demo.util

import android.content.res.Resources.getSystem

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

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

