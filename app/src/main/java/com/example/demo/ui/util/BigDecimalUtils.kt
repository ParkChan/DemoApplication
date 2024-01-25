package com.example.demo.ui.util

import java.math.BigDecimal

fun BigDecimal.isEquals(decimal: BigDecimal): Boolean = compareTo(decimal) == 0
fun BigDecimal.isOver(decimal: BigDecimal): Boolean = compareTo(decimal) == 1
fun BigDecimal.isLess(decimal: BigDecimal): Boolean = compareTo(decimal) == -1