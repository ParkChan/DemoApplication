package com.example.demo.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewHolderItemInfo(
    private var positionY: Float = 0.0f,
    private var viewHeight: Int = 0
) : Parcelable {
    fun itemPositionY(): Int = positionY.toInt()
    fun itemViewHeight(): Int = viewHeight

    fun onChangedTouchItemView(positionY: Float, viewHeight: Int) {
        this.positionY = positionY
        this.viewHeight = viewHeight
    }
}
