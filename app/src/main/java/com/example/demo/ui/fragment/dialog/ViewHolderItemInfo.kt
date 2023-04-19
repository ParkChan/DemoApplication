package com.example.demo.ui.fragment.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewHolderItemInfo(
    private var position: Int = 0,
    private var positionY: Float = 0.0f,
    private var viewHeight: Int = 0
) : Parcelable {
    fun itemPosition(): Int = position
    fun itemPositionY(): Int = positionY.toInt()
    fun itemViewHeight(): Int = viewHeight
    fun onChangedTouchItemView(position: Int, positionY: Float, viewHeight: Int) {
        this.position = position
        this.positionY = positionY
        this.viewHeight = viewHeight
    }
}
