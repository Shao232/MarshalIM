package com.marshal.android.home.adapter

import android.view.ViewGroup
import com.marshal.android.pojo.WenDaBean
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.module.wan.android.R

class WanHomeAdapter: BaseRecyclerAdapter<WanHomeViewHolder, WenDaBean>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): WanHomeViewHolder {
       return WanHomeViewHolder(R.layout.item_wan_home_child,parent)
    }

    override fun bindViewHolderData(holder: WanHomeViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemWanHomeChildBinding?.tvItemHomeChild?.text = item.title
    }
}