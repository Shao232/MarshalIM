package com.marshalim.moudle.open.question.adapter

import android.view.ViewGroup
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshalim.moudle.open.question.R

class TextContentAdapter:BaseRecyclerAdapter<TextContentViewHolder,String>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): TextContentViewHolder {
        return TextContentViewHolder(context = parent.context, R.layout.item_text_content,parent)
    }

    override fun bindViewHolderData(holder: TextContentViewHolder, position: Int) {
        val data = itemList[position]
        holder.tvContent?.text = data

    }
}