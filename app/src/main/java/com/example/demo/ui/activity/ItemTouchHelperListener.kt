package com.example.demo.ui.activity

interface ItemTouchHelperListener {
    fun onItemMove(startPosition: Int, endPosition: Int): Boolean
    fun onItemSwipe(position: Int)
    fun onDropDown()
}