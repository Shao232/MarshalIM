package com.marshal.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marshal.MApplication

abstract class BaseRecyclerAdapter<T: BaseRecyclerViewHolder,E>: RecyclerView.Adapter<T>() {

    val itemList:ArrayList<E> = arrayListOf()

    protected var mContext: Context? = null

    var itemOnClickListener:ItemOnClickListener<E>? = null

    init {
        mContext = MApplication.getInstance().applicationContext
    }

    fun setItemOnClickListener(clickListener:ItemOnClickListener<E>){
        itemOnClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {

        return onViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return  if(itemList.isEmpty()) 0 else itemList.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val bean = itemList[position]
        holder.itemView.setOnClickListener{
            if(itemOnClickListener !=null) {
                itemOnClickListener?.onClick(it,bean)
            }
        }
        bindViewHolderData(holder,position)
    }

    abstract fun onViewHolder(parent:ViewGroup,viewType: Int):T

    abstract fun bindViewHolderData(holder: T, position: Int)

}


