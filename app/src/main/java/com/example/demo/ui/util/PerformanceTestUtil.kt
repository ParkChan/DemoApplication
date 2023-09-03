package com.example.demo.ui.util

import java.text.SimpleDateFormat

object PerformanceTestUtil {
    private var count = 0
    private var prevTimeMillis = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private const val TPS_INTERVAL = 1_000
    fun sendEvent(result: (String) -> Unit, interval: Int = TPS_INTERVAL) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis - prevTimeMillis >= interval) {
            //카운트만 세어볼거면 불필요한 작업
//            val tps = count.toDouble() / ((currentTimeMillis - prevTimeMillis) / 1000.0)
//            println("TPS: $tps")
            result(makeText(interval, count))
            count = 0
            prevTimeMillis = currentTimeMillis
        }
        // 수행할 작업
        count++
    }

    fun initTpsMonitoring() {
        prevTimeMillis = System.currentTimeMillis()
        count = 0
    }

    private fun makeText(interval: Int, count: Int): String {
        val date = dateFormat.format(System.currentTimeMillis())
        val result = StringBuffer()
        result.append("$interval ms 기준 이벤트 =====")
        result.append("\n")
        result.append("Time $date")
        result.append("\n")
        result.append("Count $count")
        return result.toString()
    }
}