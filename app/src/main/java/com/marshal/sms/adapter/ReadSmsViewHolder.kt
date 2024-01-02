package com.marshal.sms.adapter

import android.view.ViewGroup
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.databinding.ItemReadSmsBinding

class ReadSmsViewHolder(parent:ViewGroup):BaseRecyclerViewHolder(R.layout.item_read_sms,parent) {

    var binding:ItemReadSmsBinding? = null

    init {
        with(itemView){
            binding = ItemReadSmsBinding.bind(this)
        }
    }

}