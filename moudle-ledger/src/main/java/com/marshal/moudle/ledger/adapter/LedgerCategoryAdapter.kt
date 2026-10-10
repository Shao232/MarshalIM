package com.marshal.moudle.ledger.adapter

import android.view.ViewGroup
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.moudle.ledger.R

/**
 * 九宫格分类选择
 */
class LedgerCategoryAdapter(private val onSelect: (String) -> Unit) :
    BaseRecyclerAdapter<LedgerCategoryViewHolder, String>() {

    private var selectedPosition = 0

    override fun onViewHolder(parent: ViewGroup, viewType: Int): LedgerCategoryViewHolder {
        return LedgerCategoryViewHolder(R.layout.item_ledger_category, parent)
    }

    override fun bindViewHolderData(holder: LedgerCategoryViewHolder, position: Int) {
        holder.bind(itemList[position], position == selectedPosition)
        holder.itemView.setOnClickListener {
            val oldPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
            itemList.getOrNull(selectedPosition)?.let { onSelect.invoke(it) }
        }
    }

    fun selectedItem(): String? = itemList.getOrNull(selectedPosition)
}
