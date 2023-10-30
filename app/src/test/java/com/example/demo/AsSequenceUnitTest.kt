package com.example.demo

import org.junit.Test

/**
 * Kotlin Collections : Eager evaluation
 * Kotlin Sequences : Lazy evaluation
 */
class AsSequenceUnitTest {

    @Test
    fun `Eager evaluation 는 수행해야 할 연산이 있으면 미루지 않고 바로 처리하는 방식`() {

        val fruits = listOf("apple", "banana", "kiwi", "cherry")

        fruits.filter {
            println("checking the length of $it")
            it.length > 5
        }.map {
            println("mapping to the length of $it")
            "${it.length}"
        }.toList()

    }

    @Test
    fun `Lazy evaluation은 지금 하지 않아도 되는 연산은 최대한 뒤로 미루는 방식`() {
        val fruits = listOf("apple", "banana", "kiwi", "cherry")
        fruits.asSequence().filter {
            println("checking the length of $it")
            it.length > 5
        }.map {
            println("mapping to the length of $it")
            "${it.length}"
        }.toList()
    }
}