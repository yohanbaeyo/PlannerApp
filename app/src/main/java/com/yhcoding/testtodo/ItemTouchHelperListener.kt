package com.yhcoding.testtodo

interface ItemTouchHelperListener {
    fun onItemMove(fromPosition: Int, toPosition:Int) : Boolean
    fun onItemSwipe(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}