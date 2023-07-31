package com.marshal.function

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class FunctionViewHolder(context: Context?, resId: Int, parent: ViewGroup) :
    BaseRecyclerViewHolder(resId, parent) {

    var tvFunction: TextView? = null

    init {
        with(itemView) {
            tvFunction = findViewById(R.id.tv_function_item)
        }
    }

}