package com.marshal.moudle.ledger.adapter

import android.content.res.ColorStateList
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.moudle.ledger.R
import com.marshal.moudle.ledger.db.LedgerBean
import com.marshal.moudle.ledger.utils.LedgerCategory
import com.marshal.moudle.ledger.utils.LedgerTimeUtils

class LedgerListViewHolder(resId: Int, parent: ViewGroup) : BaseRecyclerViewHolder(resId, parent) {

    private val tvIcon: TextView = itemView.findViewById(R.id.tv_ledger_icon)
    private val tvCategory: TextView = itemView.findViewById(R.id.tv_ledger_category)
    private val tvRemark: TextView = itemView.findViewById(R.id.tv_ledger_remark)
    private val tvAmount: TextView = itemView.findViewById(R.id.tv_ledger_amount)
    private val tvTime: TextView = itemView.findViewById(R.id.tv_ledger_time)

    fun bind(bean: LedgerBean) {
        tvIcon.text = LedgerCategory.iconText(bean.category)
        tvIcon.backgroundTintList = ColorStateList.valueOf(LedgerCategory.bgColor(bean.category))
        tvIcon.setTextColor(LedgerCategory.textColor(bean.category))

        tvCategory.text = bean.category
        tvRemark.text = if (bean.remark.isNullOrEmpty()) {
            itemView.context.getString(R.string.ledger_remark_empty)
        } else {
            bean.remark
        }

        val isIncome = bean.type == LedgerBean.TYPE_INCOME
        tvAmount.text = (if (isIncome) "+" else "−") + String.format("%.2f", bean.amount)
        tvAmount.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                if (isIncome) R.color.ledger_income else R.color.ledger_expense
            )
        )
        tvTime.text = LedgerTimeUtils.formatTime(bean.createTime)
    }
}
