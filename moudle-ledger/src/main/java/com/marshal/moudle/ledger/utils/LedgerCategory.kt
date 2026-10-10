package com.marshal.moudle.ledger.utils

import androidx.core.content.ContextCompat
import com.marshal.base_common.MApplication
import com.marshal.moudle.ledger.R

/**
 * 分类沿用 marshal 现有账本的 9 类
 */
object LedgerCategory {

    val list: List<String> = listOf(
        "居住",
        "餐饮",
        "通讯网络",
        "交通",
        "日用杂支",
        "医疗健康",
        "人情往来",
        "工具订阅",
        "其他"
    )

    fun all(): List<String> = list

    //色块里显示前两个字
    fun iconText(name: String): String = name.take(2)

    fun bgColorRes(name: String): Int = when (name) {
        "居住" -> R.color.ledger_c_house_bg
        "餐饮" -> R.color.ledger_c_food_bg
        "通讯网络" -> R.color.ledger_c_phone_bg
        "交通" -> R.color.ledger_c_traffic_bg
        "日用杂支" -> R.color.ledger_c_daily_bg
        "医疗健康" -> R.color.ledger_c_medical_bg
        "人情往来" -> R.color.ledger_c_social_bg
        "工具订阅" -> R.color.ledger_c_tool_bg
        else -> R.color.ledger_c_other_bg
    }

    fun textColorRes(name: String): Int = when (name) {
        "居住" -> R.color.ledger_c_house_text
        "餐饮" -> R.color.ledger_c_food_text
        "通讯网络" -> R.color.ledger_c_phone_text
        "交通" -> R.color.ledger_c_traffic_text
        "日用杂支" -> R.color.ledger_c_daily_text
        "医疗健康" -> R.color.ledger_c_medical_text
        "人情往来" -> R.color.ledger_c_social_text
        "工具订阅" -> R.color.ledger_c_tool_text
        else -> R.color.ledger_c_other_text
    }

    fun bgColor(name: String): Int =
        ContextCompat.getColor(MApplication.getInstance(), bgColorRes(name))

    fun textColor(name: String): Int =
        ContextCompat.getColor(MApplication.getInstance(), textColorRes(name))
}
