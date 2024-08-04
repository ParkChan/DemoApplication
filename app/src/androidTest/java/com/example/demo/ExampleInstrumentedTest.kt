package com.example.demo

import android.widget.Toast
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.demo.screen.MainScreen
import com.example.demo.ui.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.pager2.KViewPager2
import io.github.kakaocup.kakao.screen.Screen
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
        step("뷰페이저 테스트 화면 이동 테스트 코드") {
            MainScreen {
                moveViewPagerFragment {
                    isVisible()
                    click()
                    Screen.idle(2_500L) // Wait for timeout
                }

                viewPager {
                    isDisplayed()
                    scrollTo(0)
                    Screen.idle()
                    scrollTo(1)
                    Screen.idle()
                    scrollTo(2)
                    Screen.idle()
                    scrollTo(3)
                    Screen.idle(2_500L) // Wait for timeout
                }
            }
        }
    }
}