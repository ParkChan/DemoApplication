package com.example.demo

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.demo.screen.MainScreen
import com.example.demo.ui.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.screen.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

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

// ./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.demo.TestSuite
// ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.demo.TestSuite
@RunWith(Suite::class)
@Suite.SuiteClasses(
    FirstTestClass::class,  // 먼저 실행
    SecondTestClass::class // 나중에 실행
)
class TestSuite

class FirstTestClass : TestCase() {
    @Test
    fun test(){
        println("Hello 1")
    }

}
class SecondTestClass : TestCase() {
    @Test
    fun test(){
        println("Hello 2")
    }
}