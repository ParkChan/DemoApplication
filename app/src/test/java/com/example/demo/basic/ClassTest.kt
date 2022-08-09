package com.example.demo.basic

import org.junit.Test

//[원문](https://kotlinlang.org/docs/classes.html#constructors)
//[블로그](https://thdev.tech/kotlin/2017/03/09/Kotlin-Constructor-Init/)
class ClassTest {

    @Test
    fun `constructor 테스트`(){
        Sample("ABC")
        println("======================")
        Sample("ABC", 20)
        println("======================")
        Sample("ABC", 20,"2008-01-01")
        println("======================")
    }
}


class Sample {

    private val a = println("property #1")

    init {
        println("Sample init")
    }

    constructor(name: String) {
        println("name $name")
    }

    constructor(name: String, age: Int): this(name) {
        println("name $name, age $age")
    }

    constructor(name: String, age: Int, birthday: String): this(name, age) {
        println("name $name, age $age birthday $birthday")
    }

    private val b = println("property #2")
}