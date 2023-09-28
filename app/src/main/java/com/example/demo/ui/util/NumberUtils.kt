package com.example.demo.ui.util

import java.math.BigDecimal


private val numberRegex = Regex("^[-+]?[0-9]*\\.?[0-9]+\$")
fun isNumber(str: String): Boolean {
    return numberRegex.matches(str)
}

fun String.toDoubleV1(): Double {
    return if (isNumber(this)) {
        if (this.toDouble().isFinite()) {
            this.toDouble()
        } else {
            0.0
        }
    } else {
        0.0
    }
}
fun Double.toBigDecimalV1(): BigDecimal {
    return if (isFinite()) {
        toBigDecimal()
    } else {
        BigDecimal.ZERO
    }
}
