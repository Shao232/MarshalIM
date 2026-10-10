package com.marshal.moudle.ledger.adapter

import android.view.ViewGroup
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.moudle.ledger.R
import com.marshal.moudle.ledger.pojo.LedgerListItem

class LedgerListAdapter : BaseRecyclerAdapter<BaseRecyclerViewHolder, LedgerListItem>() {

    override fun getItemViewType(position: Int): Int {
        return itemList[position].type
    }

    override fun onViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return if (viewType == LedgerListItem.TYPE_DATE) {
            LedgerDateViewHolder(R.layout.item_ledger_date, parent)
        } else {
            LedgerListViewHolder(R.layout.item_ledger, parent)
        }
    }

    override fun bindViewHolderData(holder: BaseRecyclerViewHolder, position: Int) {
        val item = itemList[position]
        if (item.type == LedgerListItem.TYPE_DATE) {
            (holder as LedgerDateViewHolder).bind(item)
        } else {
            item.bean?.let { (holder as LedgerListViewHolder).bind(it) }
        }
    }
}
