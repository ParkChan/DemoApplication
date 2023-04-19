package com.example.demo.ui.fragment.common.ext

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.ui.fragment.common.CustomDecoration

@BindingAdapter(value = ["dividerHeight", "dividerPadding", "dividerColor"], requireAll = false)
fun RecyclerView.setDivider(
    dividerHeight: Float?,
    dividerPadding: Float?,
    @ColorInt dividerColor: Int?
) {
    val decoration = CustomDecoration(
        height = dividerHeight ?: 0f,
        padding = dividerPadding ?: 0f,
        color = dividerColor ?: Color.TRANSPARENT
    )
    addItemDecoration(decoration)
}

/**
 * PrecomputedTextCompat 테스트
 */
//@BindingAdapter(
//    "app:asyncText",
//    "android:textSize",
//    requireAll = false
//)
//fun asyncText(view: TextView, text: CharSequence, textSize: Int?) {
//    // first, set all measurement affecting properties of the text
//    // (size, locale, typeface, direction, etc)
//    if (textSize != null) {
//        // interpret the text size as SP
//        view.textSize = textSize.toFloat()
//    }
//    val params = TextViewCompat.getTextMetricsParams(view)
//    (view as AppCompatTextView).setTextFuture(
//        PrecomputedTextCompat.getTextFuture(text, params, null)
//    )
//}

