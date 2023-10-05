package com.example.demo.ui.fragment

import org.junit.Test

class FolderbleCheckTestFragmentTest {
    @Test
    fun `폴더블에서  WindowLayoutInfo 항목에서 type, state 값을 가져오는 테스트`(){
        // 예시 문자열을 함수에 넣어서 테스트한다
    val str1 = "HardwareFoldingFeature { Bounds { [906,0,906,2176] }, type=FOLD, state=FLAT }"
    val str2 = "HardwareFoldingFeature { Bounds { [906,0,906,2176] }, type=FOLD, state=HALF_OPENED }"

    println(FolderbleCheckTestFragment.extractTypeAndState(str1).toString())   // type: FOLD, state: FLAT
    println(FolderbleCheckTestFragment.extractTypeAndState(str2).toString())   // type: FOLD, state: HALF_OPENED
    }
}