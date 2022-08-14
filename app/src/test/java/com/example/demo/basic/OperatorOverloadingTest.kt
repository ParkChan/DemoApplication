package com.example.demo.basic

import org.junit.Test

//[블로그](https://thdev.tech/kotlin/2018/04/01/Kotlin-Operator-Overloading/)
class OperatorOverloadingTest {

    @Test
    fun `연산자 Overloading Test`() {
        operator fun Int.plus(b: Any): String {
            return "result $this $b"
        }
        println(10 + "ABC")
    }

    @Test
    fun `산술 연산자 확장하기`() {
        data class Position(val a: Int, val b: Int) {
            operator fun plus(item: Position): Position {
                return Position(a + item.a, b + item.b)
            }
        }

        val positionOne = Position(1, 2)
        val positionTwo = Position(3, 4)
        println(positionOne + positionTwo)
        // 결과 : Position(a=4, b=6)
    }

    @Test
    fun `단항 연산자 확장하기 1`() {
        data class Position(var a: Int, var b: Int) {
            operator fun unaryMinus(): Position {
                return Position(-a, -b)
            }
        }

        println(-Position(1, 2))
        println(-Position(-20, -10))
        // 결과 : Position(a=-1, b=-2)
        // 결과 : Position(a=20, b=10)

    }

    @Test
    fun `단항 연산자 확장하기 2`() {
        data class Position(var a: Int, var b: Int) {
            operator fun inc(): Position {
                return Position(a.inc(), b.inc())
            }
        }

        var position = Position(1, 2)
        println(position++)
        println(++position)

        // Position(a=1, b=2)
        // Position(a=3, b=4)
    }

    data class Position(var a: Int, var b: Int) {
        operator fun set(position: Int, value: Int) {
            when (position) {
                0 -> a = value
                1 -> b = value
                else -> throw IndexOutOfBoundsException("Invalid coordinate $position")
            }
        }

        operator fun get(position: Int): Int = when (position) {
            0 -> a
            1 -> b
            else -> throw IndexOutOfBoundsException("Invalid coordinate $position")
        }
    }

    @Test
    fun `Collection의 list,map 과 유사한 get,set 확장하기`() {
        val position = Position(10, 20)
        println(position)
        position[0] = 30
        println(position)
        println(position[1])
    }

    @Test
    fun `Destructuring Declarations(분할)`(){
        val (a, b) = Position(10, 20)
        println(a)
        println(b)
    }

    @Test
    fun `split에서 사용하기`(){
        data class DataName(val fileName: String, val extension: String)

        fun String.split(): DataName {
            val (fileName, extension) = split(".", limit = 2)
            return DataName(fileName, extension)
        }

        println("ABC.md".split())
    }
}