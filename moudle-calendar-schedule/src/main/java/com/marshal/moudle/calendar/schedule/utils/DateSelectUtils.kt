package com.marshal.moudle.calendar.schedule.utils;

import android.app.Activity
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.DatimePicker
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode
import com.github.gzuliyujiang.wheelpicker.annotation.TimeMode
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity
import com.github.gzuliyujiang.wheelpicker.entity.TimeEntity
import com.github.gzuliyujiang.wheelpicker.impl.UnitDateFormatter
import com.github.gzuliyujiang.wheelpicker.impl.UnitTimeFormatter
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout
import com.marshal.moudle.calendar.schedule.R
import java.util.Date

object DateSelectUtils {

    fun showDateSelectDialog(context: Activity, onDatePickedListener: OnDatePickedListener) {
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

    fun showDateSelectMonthDayDialog(
        context: Activity,
        year: Int, month: Int, day: Int, hour: Int,
        onDatimeListener: OnDatimePickedListener
    ) {
        val datePicker = DatimePicker(context)
        val dateWheelLayout: DatimeWheelLayout = datePicker.wheelLayout
        dateWheelLayout.setDateMode(DateMode.MONTH_DAY)
        dateWheelLayout.setTimeMode(TimeMode.HOUR_24_NO_SECOND)
        dateWheelLayout.setDateFormatter(UnitDateFormatter())
        dateWheelLayout.setTimeFormatter(UnitTimeFormatter())

        val startDate = Date(946656000000)//2000.1.1
        val startEntity = DatimeEntity()
        startEntity.date = DateEntity.target(startDate)
        startEntity.time = TimeEntity.target(0,0,0)

        val endDate = Date(4102250639000)//2099.12.30
        val endEntity = DatimeEntity()
        endEntity.date = DateEntity.target(endDate)
        endEntity.time = TimeEntity.target(23,59,59)

        dateWheelLayout.setRange(startEntity, endEntity)
        //设置默认 需要修改默认值
        val defaultEntity = DatimeEntity()

        defaultEntity.date = DateEntity.target(year,month,day)
        defaultEntity.time = TimeEntity.target(hour,0,0)
        dateWheelLayout.setDefaultValue(defaultEntity)

        dateWheelLayout.setCurtainEnabled(true)
        dateWheelLayout.setCurtainColor(context.resources.getColor(R.color.white))
        dateWheelLayout.setIndicatorEnabled(true)
        dateWheelLayout.setIndicatorColor(context.resources.getColor(R.color.white))
        dateWheelLayout.setIndicatorSize(context.resources.displayMetrics.density * 2)
        dateWheelLayout.setTextColor(context.resources.getColor(R.color.gray_line))
        dateWheelLayout.setTextSize(14 * context.resources.displayMetrics.scaledDensity)
        dateWheelLayout.setSelectedTextColor(context.resources.getColor(R.color.text_color_black))
        dateWheelLayout.setResetWhenLinkage(true,true)
        datePicker.setOnDatimePickedListener(onDatimeListener)
        datePicker.show()
    }

}