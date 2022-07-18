package com.example.demo

import org.junit.Test

/**
 * 정렬 테스트
 * sortWith
 * sortedWith
 */
class SortUnitTest {

    private val persons = mutableListOf(
        Person("Olivia", 25),
        Person("George", 15),
        Person("Olivia", 20),
        Person("Harry", 10)
    )
    class Person(val name: String, val age: Int) {
        override fun toString(): String {
            return "Person(name='$name', age=$age)"
        }
    }

    @Test
    fun `sortWith() 기능 테스트`() {

        persons.sortWith(compareBy({ it.name }, { it.age }))

        // 또는
        // persons.sortWith(compareBy(Person::name, Person::age))
        // persons.sortWith(compareBy(Person::name).thenBy(Person::age))
        // persons.sortWith(compareBy<Person> { it.name }.thenBy { it.age })
        // persons.sortWith(compareBy<Person> { it.name }.thenByDescending { it.age })

        persons.forEach {
            println("name is ${it.name} age is ${it.age}")
        }
    }

    @Test
    fun `원래 목록을 수정하지 않고 정렬된 목록을 얻으려면 sortedWith() 사용`() {

        persons.sortedWith(compareBy({ it.name }, { it.age })).forEach(::println)

        // persons.sortedWith(compareBy(Person::name, Person::age)).forEach(::println)
        // persons.sortedWith(compareBy<Person> { it.name }.thenBy { it.age }).forEach(::println)
        // persons.sortedWith(compareBy<Person> { it.name }.thenByDescending { it.age })
        //    .forEach(::println)

    }


}



