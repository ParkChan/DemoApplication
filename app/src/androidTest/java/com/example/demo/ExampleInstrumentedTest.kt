package com.example.demo

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.demo.screen.MainScreen
import com.example.demo.ui.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun useAppContext() = run {
        step("이동 테스트 ") {
            MainScreen {
                viewpagerButton {
                    isVisible()
                    click()
                }
            }
        }
    }
}