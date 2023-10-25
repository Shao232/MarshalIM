package com.marshal.moudle.calendar.schedule.utils;

import android.app.Activity
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.impl.UnitDateFormatter
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout
import com.marshal.moudle.calendar.schedule.R
import java.util.Date

object DateSelectUtils {

    fun showDateSelectDialog(context:Activity,onDatePickedListener: OnDatePickedListener){
        val datePicker = DatePicker(context)
        val dateWheelLayout: DateWheelLayout = datePicker.wheelLayout
        dateWheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY)
        dateWheelLayout.setDateFormatter(UnitDateFormatter())
        val startDate = Date(946656000000)//2000.1.1
        val startEntity = DateEntity.target(startDate)
        val endDate = Date(4102250639000)//2099.12.30
        val endEntity = DateEntity.target(endDate)
        dateWheelLayout.setRange(startEntity, endEntity)
        dateWheelLayout.setDefaultValue(DateEntity.today())
        dateWheelLayout.setCurtainEnabled(true)
        dateWheelLayout.setCurtainColor(context.resources.getColor(R.color.white))
        dateWheelLayout.setIndicatorEnabled(true)
        dateWheelLayout.setIndicatorColor(context.resources.getColor(R.color.white))
        dateWheelLayout.setIndicatorSize(context.resources.displayMetrics.density * 2)
        dateWheelLayout.setTextColor(context.resources.getColor(R.color.gray_line))
        dateWheelLayout.setTextSize(14 * context.resources.displayMetrics.scaledDensity)
        dateWheelLayout.setSelectedTextColor(context.resources.getColor(R.color.text_color_black))
        dateWheelLayout.setResetWhenLinkage(false)
        datePicker.setOnDatePickedListener(onDatePickedListener)
        datePicker.show()
    }

}