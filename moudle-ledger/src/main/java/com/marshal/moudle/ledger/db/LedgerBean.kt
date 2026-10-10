package com.marshal.moudle.ledger.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 一条账目
 */
@Entity(tableName = "ledger")
data class LedgerBean(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    //金额，单位元
    var amount: Double = 0.0,

    //分类：居住/餐饮/通讯网络/交通/日用杂支/医疗健康/人情往来/工具订阅/其他
    var category: String = "",

    //备注
    var remark: String = "",

    //记账时间
    var createTime: Long = System.currentTimeMillis(),

    //类型：1支出 2收入
    var type: Int = TYPE_EXPENSE
) {

    companion object {
        const val TYPE_EXPENSE = 1
        const val TYPE_INCOME = 2
    }
}
