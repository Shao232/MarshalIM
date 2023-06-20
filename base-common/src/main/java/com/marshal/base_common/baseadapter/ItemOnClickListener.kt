package com.marshal.base_common.baseadapter
import android.view.View

interface AdapterItemOnClickListener<E> {
    fun onClick(view: View,position:Int){}
    fun onClick(view: View,bean:E){}
}