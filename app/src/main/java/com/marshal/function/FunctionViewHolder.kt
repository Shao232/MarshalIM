package com.marshal.function

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class FunctionViewHolder(context: Context?,parent: ViewGroup):BaseRecyclerViewHolder(context, R.layout.item_function,parent) {

    val tvFunction:TextView = itemView.findViewById(R.id.tv_function_item)

}