package com.example.demo.coroutine

import org.junit.Test
import java.util.*

class KotlinCollectionBasicUnitTest {

    @Test
    fun `고정 목록 생성 테스트`() {
        val n = 5
        val items1: List<Int> = List(n) { 0 }
        println("items1 is $items1")

        val items2: List<Int> = (1..n).map { 0 }
        println("items2 is $items2")

        //Collections.nCopies() 함수는 지정된 객체의 지정된 복사본의 변경할 수 없는 목록을 반환
        val items3: List<Int> = Collections.nCopies(n, 0)
        println("items3 is $items3")


        val size = 5
        val value = 1
        //지정된 크기의 어레이을 만들고 기본 fill() 주어진 값으로 초기화
        val data = arrayOfNulls<Int>(size)
        data.fill(value)

        val items4: List<Int> = data.toList() as List<Int>
        println("items4 is $items4")

        val items5: MutableList<Int> = data.toList() as MutableList<Int>
        items5.removeLast()
        println("items5 is $items5")
    }


    private data class Person(val name: String, val age: Int)

    private val canBeInClub27 = { p: Person -> p.age <= 27 }

    @Test
    fun `Map Test`() {
        val people = listOf(
            Person("Alice", 29),
            Person("Bob", 31),
            Person("Charles", 31)
        )
        println(people.map { it.name })
    }

    @Test
    fun `Find Test`() {
        val people = listOf(
            Person("Alice", 27),
            Person("Bob", 31),
            Person("Alice2", 27),
        )
        println(people.find(canBeInClub27))
    }

    @Test
    fun `GroupBy Test`() {
        val people = listOf(
            Person("Alice", 31),
            Person("Bob", 29),
            Person("Carol", 31)
        )
        println(people.groupBy { it.age })
    }

    @Test
    fun `flatMap Test`() {
        val people = listOf(
            "abc",
            "edf",
            "ghi",
        )
        println(people.flatMap { it.toList() })
    }

    @Test
    fun `flatten Test`() {
        val people = listOf(
            listOf("a", "b", "c"),
            listOf("가", "나", "다")
        )
        println(people.flatten())
    }

    @Test
    fun `list add Test`() {
        val abc = listOf("a", "b", "c")
        val def = listOf("가", "나", "다")
        println(abc + def)
        println(abc.plus(def))
    }

    @Test
    fun `withIndex Test`() {
        val chars = listOf("a", "b", "c", "가", "나", "다")
        for ((index, char) in chars.withIndex()) {
            println("index : $index  value: $char")
        }
    }

    @Test
    fun `중복제거`() {
        val data = arrayOf(1, 3, 6, 4, 1, 2)
        println(data.distinct())

    }

    @Test
    fun `sort 메소드는 해당 Collection 의 원소 위치가 변경됩니다`() {
        val data = arrayOf(1, 3, 6, 4, 1, 2)
        data.sort()
        print("sort Data ${data.toList()}")
    }

    @Test
    fun `sorted 메소드를 사용하면 기존 Collection 은 변하지 않습니다`() {
        val data = arrayOf(1, 3, 6, 4, 1, 2)
        print("sortedData ${data.sorted()}")
        println()
        print("originData ${data.toList()}")
        println()
    }
}