package com.example.demo.basic

import org.junit.Test

class KotlinException {

    // IllegalArgumentException 은 일반적으로 매개변수로 전달한 값이 적절하지 않을 때 발생시키는 Exception
    @Test
    fun `Kotlin 예외처리 require()`() {
        fun calculate(value: String?) {
            // 적용 전
            if(value.isNullOrEmpty()) {
                throw IllegalArgumentException("값이 null 혹은 공백일 수 없습니다.")
            }

            // 적용 후
            require(!value.isNullOrEmpty()) { "입력값이 null 혹은 공백일 수 없습니다."}
        }
    }


    enum class Operator(val op: String) {
        PLUS("+"), MINUS("-")
    }
    @Test
    fun `Kotlin 예외처리 requireNotNull()`() {

        val searchText = "wine"

        // 적용 전
        val fruits = listOf("apple", "banana", "kiwi", null)
        fruits.firstOrNull { it == searchText } ?: throw IllegalArgumentException("사칙연산 기호가 아닙니다.")

        // 적용 후
//        requireNotNull(fruits.firstOrNull { it == searchText }) { "와인이 없습니다." }
    }

    // IllegalStateException 은 메소드를 호출할 상태(State)가 아닐 때 발생시키는 Exception
    @Test
    fun `Kotlin 예외처리 check()`() {
        fun connect(isConnected: Boolean, connectJob: () -> Unit) {
            // 적용 전
            if(isConnected) {
                throw IllegalArgumentException("이미 연결되어있습니다.")
            }

            // 적용 후
            check(!isConnected) { "이미 연결되어 있습니다" }

            connectJob()
        }
    }

    @Test
    fun `Kotlin 예외처리 checkNotNull()`() {
        var connectedState: String? = null

        fun connect(connectJob: () -> Unit) {
            // 적용 전
            val state = if (connectedState == null) {
                throw IllegalStateException("State is null")
            } else {
                connectedState
            }

            // 적용 후
            val state2 = checkNotNull(connectedState)

            connectJob()
        }
    }
}