package com.marshal.moudle.ledger.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object LedgerTimeUtils {

    //本月第一天 00:00:00
    fun monthStart(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    //本月最后一天 23:59:59
    fun monthEnd(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    //日期标题：10月3日
    fun dayLabel(time: Long): String {
        val format = SimpleDateFormat("M月d日", Locale.getDefault())
        return format.format(Date(time))
    }

    //流水右侧时间：12:30
    fun formatTime(time: Long): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(Date(time))
    }

    //录入页底部：今天 10-03 12:30
    fun fullLabel(time: Long): String {
        val format = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        return format.format(Date(time))
    }

    //是否同一天
    fun isSameDay(first: Long, second: Long): Boolean {
        val a = Calendar.getInstance().apply { timeInMillis = first }
        val b = Calendar.getInstance().apply { timeInMillis = second }
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)
    }
}
