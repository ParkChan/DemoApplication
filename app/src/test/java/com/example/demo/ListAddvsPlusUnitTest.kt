package com.example.demo

import org.junit.Test

/**
 * https://stackoverflow.com/questions/57770663/difference-between-plus-vs-add-in-kotlin-list
 */
class ListPlusUnitTest {

    @Test
    fun `List plus`() {
        val result = listOf(1, 2, 3) + 4 // [1,2,3,4]
        val result2 = listOf(1, 2, 3).plus(4) // [1,2,3,4]
        val result3 = listOf(1, 2, 3) + listOf(4, 5, 6) // [1, 2, 3, 4, 5, 6]
        val result4 = listOf(1, 2, 3).plus(listOf(4, 5, 6)) // [1, 2, 3, 4, 5, 6]

        val test = ArrayList<Int>()
        val arrayListAddResult = test.add(1)    //수정 여부에 따른 결과 리턴
        println(arrayListAddResult)

    }
    //List plus의 내부동작
    //public operator fun <T> Collection<T>.plus(elements: Iterable<T>): List<T> {
    //    if (elements is Collection) {
    //        val result = ArrayList<T>(this.size + elements.size)
    //        result.addAll(this)
    //        result.addAll(elements)
    //        return result
    //    } else {
    //        val result = ArrayList<T>(this)
    //        result.addAll(elements)
    //        return result
    //    }
    //}

}