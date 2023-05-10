package com.marshal.mainadapter

import android.view.View
import android.widget.TextView
import com.marshal.baseadapter.BaseRecyclerViewHolder
import com.marshal.R

class MainFragmentViewHolder(itemView: View): BaseRecyclerViewHolder(itemView) {
    var tvMainItem:TextView? = null
    init {
        with(itemView) {
            tvMainItem = findViewById(R.id.tv_main_item)
        }
    }

}