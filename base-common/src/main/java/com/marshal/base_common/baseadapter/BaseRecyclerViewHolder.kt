package com.marshal.base_common.baseadapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewHolder(context: Context?,resId:Int,parent: ViewGroup)
    :RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(resId,parent,false)){


}