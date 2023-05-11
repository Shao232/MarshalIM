package com.marshal.baseadapter

import android.view.View

interface ItemOnClickListener<E> {
    fun onClick(view: View,position:Int){}
    fun onClick(view: View,bean:E){}
}