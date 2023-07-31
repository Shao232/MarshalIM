package com.marshal.base_common.baseadapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marshal.base_common.MApplication

open class BaseRecyclerViewHolder(resId:Int,parent: ViewGroup)
    :RecyclerView.ViewHolder(LayoutInflater.from(MApplication.getInstance().applicationContext).inflate(resId,parent,false)){


}