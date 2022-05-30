package com.example.demo

import com.example.demo.fragment.common.model.Event
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * gist.github.com/JoseAlcerreca/5b661f1800e1e654f07cc54fe87441af#file-event-kt
 */
class EventTest {

    private lateinit var event: Event<Boolean>

    @Before
    fun setup() {
        event = Event(value = true)
    }

    @Test
    fun `최초 접근 시_데이터를 전달합니다`() {
        // given

        // when
        val actual = event.isActive()

        // then
        assertTrue(actual)
    }

    @Test
    fun `두 번 이상 접근 시_데이터를 전달하지 않습니다`() {
        // given
        event.isActive()

        // when
        val actual = event.isActive()

        // then
        assertFalse(actual)
    }
}
