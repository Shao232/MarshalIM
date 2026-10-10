package com.marshal.moudle.ledger.adapter

import android.view.ViewGroup
import android.widget.TextView
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.moudle.ledger.R
import com.marshal.moudle.ledger.pojo.LedgerListItem
import com.marshal.moudle.ledger.utils.LedgerTimeUtils

class LedgerDateViewHolder(resId: Int, parent: ViewGroup) : BaseRecyclerViewHolder(resId, parent) {

    private val tvDateLabel: TextView = itemView.findViewById(R.id.tv_date_label)
    private val tvDateTotal: TextView = itemView.findViewById(R.id.tv_date_total)

    fun bind(item: LedgerListItem) {
        tvDateLabel.text = LedgerTimeUtils.dayLabel(item.date)
        tvDateTotal.text = itemView.context.getString(
            R.string.ledger_day_summary,
            item.dayCount,
            String.format("%.2f", item.dayTotal)
        )
    }
}
