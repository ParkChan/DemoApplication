package com.example.demo.screen

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import com.example.demo.R
import io.github.kakaocup.kakao.pager2.KViewPager2
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val moveViewPagerFragment = KButton { withId(R.id.btn_nav) }

    // KViewPager2 초기화
    val viewPager = KViewPager2(builder = { withId(R.id.vp_sample) }, {})

}
