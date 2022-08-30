package com.example.demo.basic

import org.junit.Test


class InfinityAndNanTest {

    @Test
    fun `Infinity는 무한대`() {

        val n = 10
        println(n / 0.0)

    }

    @Test
    fun `NaN은 (Not a Number) 숫자가 아니다`() {

        val n = 10
        println(n % 0.0)
    }

    @Test
    fun `Infinity 나 NaN 인지 체크`() {

        fun check(number: Double) {
            if (number.isInfinite() || number.isNaN()) {
                println("사용 불가")
            } else {
                println("사용 가능")
            }
        }

        val a: Double = 10 / 0.0
        val b: Double = 10 % 0.0
        val c: Double = (10 / 2).toDouble()
        val d: Double = (10 % 3).toDouble()

        check(a)
        check(b)
        check(c)
        check(d)
    }
}