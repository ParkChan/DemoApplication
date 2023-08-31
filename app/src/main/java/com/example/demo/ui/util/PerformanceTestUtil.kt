package com.example.demo.ui.util

object PerformanceTestUtil {
    private var count = 0
    private var prevTimeMillis = System.currentTimeMillis()
    fun startTpsMonitoring(result: (Int) -> Unit) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis - prevTimeMillis >= 1000) {
            //카운트만 세어볼거면 불필요한 작업
//            val tps = count.toDouble() / ((currentTimeMillis - prevTimeMillis) / 1000.0)
//            println("TPS: $tps")
            result(count)
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