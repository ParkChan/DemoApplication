package com.example.demo.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import timber.log.Timber

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var tempText: String = ""
    var suffix: String = ""
    var isSuffixNextLine = false
        private set

    override fun onDraw(canvas: Canvas) {
        val textPaint: TextPaint = paint
        val text = tempText
        val availableWidth = width - paddingLeft - paddingRight

        val layout = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, textPaint, availableWidth)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(
                text,
                textPaint,
                availableWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                false
            )
        }

        val lineCount = layout.lineCount
        if (lineCount > 0) {
            val lastLineStart = layout.getLineStart(lineCount - 1)
            val lastLineEnd = layout.getLineEnd(lineCount - 1)
            val lastLineText = text.substring(lastLineStart, lastLineEnd)
            val lastLineWidth = textPaint.measureText(lastLineText)
            val suffixWidth = textPaint.measureText(suffix)

            isSuffixNextLine = if (lastLineWidth + suffixWidth > availableWidth) {
                true
            } else {
                false
            }
        } else {
            isSuffixNextLine = false
        }
        super.onDraw(canvas)
    }
}


