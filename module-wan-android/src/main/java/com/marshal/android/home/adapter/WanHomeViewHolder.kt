package com.marshal.android.home.adapter

import android.view.ViewGroup
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.module.wan.android.databinding.ItemWanHomeChildBinding

class WanHomeViewHolder(resId: Int, parent: ViewGroup):BaseRecyclerViewHolder(resId,parent) {

    var itemWanHomeChildBinding:ItemWanHomeChildBinding? = null

    init {
        with(itemView) {
            itemWanHomeChildBinding = ItemWanHomeChildBinding.bind(this)
        }
    }

}