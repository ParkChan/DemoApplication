package com.example.demo.util

import android.app.Activity
import androidx.core.content.ContextCompat
import com.example.demo.R

object StatusBarUtil {
    fun setStatusBarColorAndNavigationColor(activity: Activity, colorType: SystemBarColorType) {
        setStatusBarColor(activity,colorType)
        setNavigationBarColor(activity,colorType)
    }
    fun setStatusBarColor(activity: Activity, colorType: SystemBarColorType) {
        activity.window.statusBarColor =
            ContextCompat.getColor(activity, colorType.backgroundColorId)
    }
    fun setNavigationBarColor(activity: Activity, colorType: SystemBarColorType) {
        activity.window.navigationBarColor =
            ContextCompat.getColor(activity, colorType.backgroundColorId)
    }
}

enum class SystemBarColorType(val backgroundColorId: Int) {
    // 색 지정
    DEFAULT_STATUS_BAR(R.color.white), DARK_STATUS_BAR(R.color.black)
}