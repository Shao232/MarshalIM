package com.marshal.function

import android.view.ViewGroup
import com.marshal.R
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.pojo.FunctionBean

class FunctionAdapter : BaseRecyclerAdapter<FunctionViewHolder, FunctionBean>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
        return FunctionViewHolder(context = mContext, R.layout.item_fun_layout, parent)
    }

    override fun setAdapterItemOnClickListener(clickListener: AdapterItemOnClickListener<FunctionBean>) {
        super.setAdapterItemOnClickListener(clickListener)
    }

    override fun bindViewHolderData(holder: FunctionViewHolder, position: Int) {
        val bean = itemList[position]
        holder.tvFunction?.text = bean.functionName
        holder.tvFunctionIcon?.text = bean.functionName.firstOrNull()?.toString() ?: ""
    }
}
