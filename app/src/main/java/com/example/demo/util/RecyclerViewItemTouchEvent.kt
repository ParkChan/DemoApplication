package com.example.demo.util

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.dialog.ViewHolderItemInfo

/**
 * RecyclerView 에서 선택한 뷰홀더 아이템의 뷰정보(하단 y값, 뷰높이)를 가져오는 기능
 */
class RecyclerViewItemTouchEvent(
    private val recyclerView: RecyclerView
) {
    private val gestureDetector by lazy {
        GestureDetector(recyclerView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }
        })
    }

    val itemViewPositionInfo by lazy {
        ViewHolderItemInfo()
    }

    private val onItemTouchListener = object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child: View? = rv.findChildViewUnder(e.x, e.y)
            if (child != null && gestureDetector.onTouchEvent(e)) {
                itemViewPositionInfo.onChangedTouchItemView(
                    child.y,
                    child.height
                )
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

    init {
        recyclerView.addOnItemTouchListener(onItemTouchListener)
    }
}