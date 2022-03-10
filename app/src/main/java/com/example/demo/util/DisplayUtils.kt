package com.example.demo.util

import android.content.Context
import kotlin.math.roundToInt

fun convertDpToPx(context: Context, dp: Int): Int =
    (dp * context.resources.displayMetrics.density).roundToInt()