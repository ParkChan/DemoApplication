package com.example.demo.activity

interface ItemTouchHelperListener {
    fun onItemMove(startPosition: Int, endPosition: Int): Boolean
    fun onItemSwipe(position: Int)
}