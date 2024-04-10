package com.marshal.moudle.calendar.schedule.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.moudle.calendar.schedule.R
import com.marshal.moudle.calendar.schedule.databinding.FragmentCalendarWeekBinding
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleBean
import com.marshal.moudle.calendar.schedule.ui.viewmodel.CalendarViewModel
import com.necer.enumeration.CalendarState


class CalendarWeekFragment : BaseViewFragment<FragmentCalendarWeekBinding>() {


    private var viewType = SCHEDULE_MONTH

    /**
     * 当前日历对应的时间戳
     */
    private var curTimeMillis: Long = 0
    private val viewModel: CalendarViewModel? by viewModels()

    private var scheduleBean: CalendarScheduleBean? = null;

    override fun getResLayoutId(): Int {
        return R.layout.fragment_calendar_week
    }

    override fun getResLayoutBinding(): View? {
        binding = FragmentCalendarWeekBinding.inflate(layoutInflater)
        return binding?.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        if (scheduleBean == null) {
            scheduleBean = CalendarScheduleBean(0, 0, 0, 0, 0, 0, 0, 0, 0, -1);
        }

       binding?.customCalendar?.setBenderSuccess(true)



    }

    override fun subscribeBack() {
        Log.d("TAG", "当前时间戳 :$curTimeMillis")
//        viewModel?.getScheduleData(curTimeMillis.toString())

//        viewModel?.responseResult?.observe(this) { aBoolean ->
//            if (!aBoolean) {
//                if (getAppInfoToken()?.isEmpty() == true) {
//                    showToast("请重新登录")
//                }
//            }
//        }
//
//        viewModel?.errorData?.observe(this) { throwable ->
//            if (throwable.message != null) {
//                showToast(throwable?.message ?: "")
//            }
//        }
//
//        viewModel?.responseScheduleLiveData?.observe(this) { arrayList ->
//            Log.d("TAG", "fragment list.size:" + arrayList.size)
//            getScheduleModels(arrayList)
//        }

    }

    fun updateScheduleList() {
//       if (viewType != 1 && curTimeMillis != 0L) {
//           viewModel?.getScheduleData(curTimeMillis.toString())
//       }

    }

    private fun startAddSchedulePage() {
        val intent = Intent(activity, AddCalendarScheduleActivity::class.java)
        Log.d("TAG", "weekFragment scheduleBean :" + scheduleBean.toString());
        intent.putExtra("selectTime", scheduleBean)
        activity?.startActivityForResult(intent, 1413);
    }


    /**
     * 设置视图,切换视图
     *
     * @param type 2 周视图 3 日视图
     */
    fun setViewType(type: Int) {
        viewType = type
        switchAndShowView()
    }

//    private fun getScheduleModels(scheduleList: ArrayList<DayScheduleBean>) {
//    }


    /**
     * 切换或者显示视图
     */
    private fun switchAndShowView() {

        when (viewType) {
            SCHEDULE_MONTH -> {
                binding?.customCalendar?.toMonth()
                binding?.customCalendar?.calendarState = CalendarState.MONTH
            }
            SCHEDULE_WEEK -> {
                binding?.customCalendar?.toWeek()
                binding?.customCalendar?.calendarState = CalendarState.WEEK
            }

            SCHEDULE_DAY -> {
                binding?.customCalendar?.toWeek()
                binding?.customCalendar?.calendarState = CalendarState.WEEK
            }

            else -> {}
        }
    }

    companion object {
        //视图类型
        const val SCHEDULE_MONTH = 1
        const val SCHEDULE_WEEK = 2
        const val SCHEDULE_DAY = 3
    }
}