package com.marshal.function

import SizeUtils
import android.view.ViewGroup
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.pojo.FunctionBean

class FunctionAdapter : BaseRecyclerAdapter<FunctionViewHolder, FunctionBean>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
        return FunctionViewHolder(context = mContext, parent)
    }

    override fun setAdapterItemOnClickListener(clickListener: AdapterItemOnClickListener<FunctionBean>) {
        super.setAdapterItemOnClickListener(clickListener)
    }

    override fun bindViewHolderData(holder: FunctionViewHolder, position: Int) {
        val bean = itemList[position]
        //设置条目的外间距
        val layoutParams = holder.tvFunction.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = SizeUtils.dip2px(mContext,100f)
        layoutParams.height = SizeUtils.dip2px(mContext,100f)
        val positionIndex = position +1
        //如果是第三个条目设置下右间距，不是的话就不设置
        if(position!=0 && positionIndex % 3 == 0) {
            layoutParams.setMargins(SizeUtils.dip2px(mContext,8f),SizeUtils.dip2px(mContext,8f),SizeUtils.dip2px(mContext,8f),0)
        }else {
            layoutParams.setMargins(SizeUtils.dip2px(mContext,8f),SizeUtils.dip2px(mContext,8f),0,0)
        }
        holder.tvFunction.layoutParams = layoutParams
        holder.tvFunction.text = bean.functionName

    }
}