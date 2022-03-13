package com.example.demo.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * recyclerViewYAxis : RecyclerView Y축
 * positionY : Y축
 * viewWidth : 가로
 * viewHeight : 높이
 */

@Parcelize
data class ViewHolderItemInfo(
    private val recyclerViewYAxis: Int = 0,
    private val positionY: Float = 0.0f,
    private val viewWidth: Int = 0,
    private val viewHeight: Int = 0
) : Parcelable {
    fun recyclerViewYAxis(): Int = recyclerViewYAxis
    fun itemPositionY(): Int = positionY.toInt()
    fun itemViewWidth(): Int = viewWidth
    fun itemViewHeight(): Int = viewHeight
}
