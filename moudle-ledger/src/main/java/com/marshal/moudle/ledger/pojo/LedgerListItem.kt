package com.marshal.moudle.ledger.pojo

import com.marshal.moudle.ledger.db.LedgerBean

/**
 * 列表项：日期分组标题 + 流水记录两种
 */
data class LedgerListItem(
    val type: Int = TYPE_RECORD,
    val bean: LedgerBean? = null,
    val date: Long = 0L,
    val dayCount: Int = 0,
    val dayTotal: Double = 0.0
) {

    companion object {
        const val TYPE_DATE = 1
        const val TYPE_RECORD = 2
    }
}
