package com.marshal.mine.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class BlueToothCustomViewHolder(context: Context?, resId:Int,
                                parent: ViewGroup
): BaseRecyclerViewHolder(resId, parent) {

    var tvStr:TextView? = null

    init {
        with(itemView){
            tvStr =  findViewById(R.id.tv_str)
        }
    }

}