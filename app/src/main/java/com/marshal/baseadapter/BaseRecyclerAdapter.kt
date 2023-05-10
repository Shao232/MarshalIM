package com.marshal.baseadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T: BaseRecyclerViewHolder,E>: RecyclerView.Adapter<T>() {

    val itemList:ArrayList<E> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return onViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return  if(itemList.isEmpty()) 0 else itemList.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        bindViewHolderData(holder,position)
    }

    abstract fun onViewHolder(parent: ViewGroup,viewType: Int):T

    abstract fun bindViewHolderData(holder: T, position: Int)

}


