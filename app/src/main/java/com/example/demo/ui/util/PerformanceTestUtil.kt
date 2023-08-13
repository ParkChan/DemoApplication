package com.example.demo.ui.util

object PerformanceTestUtil {
    private var count = 0
    private var prevTimeMillis = System.currentTimeMillis()
    fun startTpsMonitoring() {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - prevTimeMillis >= 1000) {
            val tps = count.toDouble() / ((currentTimeMillis - prevTimeMillis) / 1000.0)
            println("TPS: $tps")
            println("TPS: Count $count")
            count = 0
            prevTimeMillis = currentTimeMillis
        }
        // 수행할 작업
        count++
    }
    fun initTpsMonitoring(){
        prevTimeMillis = System.currentTimeMillis()
        count = 0
    }
}