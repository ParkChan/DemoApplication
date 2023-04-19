package com.example.demo.ui.util

import android.app.Activity
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.demo.R

object StatusBarUtil {
    fun setStatusBarColorAndNavigationColor(activity: Activity, colorType: SystemBarColorType) {
        setStatusBarColor(activity,colorType)
        setNavigationBarColor(activity,colorType)
    }
    private fun setStatusBarColor(activity: Activity, colorType: SystemBarColorType) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.statusBarColor =
            ContextCompat.getColor(activity, colorType.backgroundColorId)
    }
    private fun setNavigationBarColor(activity: Activity, colorType: SystemBarColorType) {
        activity.window.navigationBarColor =
            ContextCompat.getColor(activity, colorType.backgroundColorId)
    }
}

enum class SystemBarColorType(val backgroundColorId: Int) {
    // 색 지정
    DEFAULT_STATUS_BAR(R.color.gray), DARK_STATUS_BAR(R.color.black)
}