package com.marshal.moudle.ledger.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.moudle.ledger.R

class LedgerCategoryViewHolder(resId: Int, parent: ViewGroup) :
    BaseRecyclerViewHolder(resId, parent) {

    private val tvCategoryName: TextView = itemView.findViewById(R.id.tv_category_name)

    fun bind(name: String, selected: Boolean) {
        tvCategoryName.text = name
        tvCategoryName.isSelected = selected
        tvCategoryName.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                if (selected) R.color.ledger_brand else R.color.ledger_text_sub
            )
        )
    }
}
