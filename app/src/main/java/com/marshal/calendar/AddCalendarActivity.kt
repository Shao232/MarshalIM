package com.marshal.calendar

import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.impl.UnitDateFormatter
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout
import com.marshal.AppRouterPath
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityAddCalendarBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Route(path = AppRouterPath.APP_ADD_EVENT_CALENDAR)
class AddCalendarActivity : BaseViewActivity<ActivityAddCalendarBinding>() {

    private var date:Date? = null

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityAddCalendarBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("添加日程")
        }

        binding?.tvSelectDate?.setOnClickListener {
            val datePicker = DatePicker(this)
            val dateWheelLayout: DateWheelLayout = datePicker.wheelLayout
            dateWheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY)
            dateWheelLayout.setDateFormatter(UnitDateFormatter())
            val date = Date(315504000000)//2020.1.1
            val dateEntity = DateEntity.target(date)
            dateWheelLayout.setRange(dateEntity, DateEntity.monthOnFuture(48))
            dateWheelLayout.setDefaultValue(DateEntity.today())
            dateWheelLayout.setCurtainEnabled(true)
            dateWheelLayout.setCurtainColor(resources.getColor(R.color.white))
            dateWheelLayout.setIndicatorEnabled(true)
            dateWheelLayout.setIndicatorColor(resources.getColor(R.color.white))
            dateWheelLayout.setIndicatorSize(it.resources.displayMetrics.density * 2)
            dateWheelLayout.setTextColor(resources.getColor(R.color.gray_line))
            dateWheelLayout.setTextSize(14 * resources.displayMetrics.scaledDensity)
            dateWheelLayout.setSelectedTextColor(resources.getColor(R.color.text_color_black))
            dateWheelLayout.setResetWhenLinkage(false)
            datePicker.setOnDatePickedListener { year, month, day ->
                Log.d("TAG", "year:${year}, month:${month}, day:${day}")
                val calendar = Calendar.getInstance()
                calendar.set(year, month-1, day)
                this.date = calendar.time
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateString = simpleDateFormat.format(calendar.time)
                Log.d("TAG", "date:${dateString}")
                binding?.tvSelectDate?.text = dateString

            }
            datePicker.show()

        }

        binding?.btnSetCalendar?.setOnClickListener {

            val titleEvent = binding?.editInputEventTitle?.text?.toString()?.trim()
            val contentEvent = binding?.editInputEventContent?.text?.toString()?.trim()
            val dateStr = binding?.tvSelectDate?.text?.toString()?.trim()

            if(titleEvent.isNullOrEmpty() || contentEvent.isNullOrEmpty() || dateStr.isNullOrEmpty()) {
                showToast("请输入内容，不要为空")
                return@setOnClickListener
            }


            CalendarReminderUtils.addCalendarEventRemind(this,titleEvent,contentEvent,
                date?.time?:0,date?.time?:0,0,object:CalendarReminderUtils.onCalendarRemindListener{
                    override fun onFailed(error_code: CalendarReminderUtils.onCalendarRemindListener.Status?) {
                        Log.e("TAG","onFailed:error_code:${error_code}")
                    }

                    override fun onSuccess() {
                       Log.d("TAG","添加提醒成功")
                    }

                })

        }


    }
}