package com.example.demo.screen

import com.example.demo.ui.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import com.example.demo.R

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val viewpagerButton = KButton { withId(R.id.btn_nav) }

}
