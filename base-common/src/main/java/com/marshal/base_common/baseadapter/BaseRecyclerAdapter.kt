package com.marshal.base_common.baseadapter

import NoShakeBtnUtil
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marshal.base_common.MApplication

abstract class BaseRecyclerAdapter<T : BaseRecyclerViewHolder, E> : RecyclerView.Adapter<T>() {

    val itemList: ArrayList<E> = arrayListOf()

    protected var mContext: Context? = null

    var itemOnClickListener: AdapterItemOnClickListener<E>? = null

    init {
        mContext = MApplication.getInstance().applicationContext
    }

    open fun setAdapterItemOnClickListener(clickListener: AdapterItemOnClickListener<E>) {
        itemOnClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return try {
            onViewHolder(parent, viewType)
        } catch (error: Throwable) {
            throw error
        }
    }

    override fun getItemCount(): Int {
        return if (itemList.isEmpty()) 0 else itemList.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val bean = itemList[position]
        holder.itemView.setOnClickListener {
            if (itemOnClickListener != null) {
                if (NoShakeBtnUtil.isFastDoubleClick(it)) {
                    return@setOnClickListener
                }
                itemOnClickListener?.onClick(it, bean)
                itemOnClickListener?.onClick(it, position)
            }
        }
        bindViewHolderData(holder, position)
    }

    abstract fun onViewHolder(parent: ViewGroup, viewType: Int): T

    abstract fun bindViewHolderData(holder: T, position: Int)

    fun addListAll(list: ArrayList<E>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }


}


