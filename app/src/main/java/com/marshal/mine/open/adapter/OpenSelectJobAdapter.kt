package com.marshal.mine.open.adapter

import android.view.ViewGroup
import android.widget.TextView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class OpenSelectJobAdapter : BaseRecyclerAdapter<OpenSelectJobViewHolder, String>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenSelectJobViewHolder {
        return OpenSelectJobViewHolder(parent)
    }

    override fun bindViewHolderData(holder: OpenSelectJobViewHolder, position: Int) {
        val str = itemList[position]
        holder.tvStr?.text = str
    }
}


class OpenSelectJobViewHolder(parent: ViewGroup) :
    BaseRecyclerViewHolder(resId = R.layout.item_string, parent = parent) {

    var tvStr: TextView? = null

    init {
        with(itemView) {
            tvStr = findViewById(R.id.tv_str)
        }
    }

}