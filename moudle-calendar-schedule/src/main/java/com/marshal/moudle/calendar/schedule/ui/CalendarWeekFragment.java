package com.marshal.moudle.calendar.schedule.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.marshal.base_common.baseview.BaseViewFragment;
import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.databinding.FragmentCalendarWeekBinding;
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleBean;
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleViewBean;
import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean;
import com.marshal.moudle.calendar.schedule.ui.AddCalendarScheduleActivity;
import com.marshal.moudle.calendar.schedule.widget.CustomDayCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class CalendarWeekFragment extends BaseViewFragment<FragmentCalendarWeekBinding> {

    //视图类型
    public static final int SCHEDULE_MONTH = 1;
    public static final int SCHEDULE_WEEK = 2;
    public static final int SCHEDULE_DAY = 3;
    private int viewType = SCHEDULE_MONTH;

    private CalendarScheduleBean scheduleBean;
    private ArrayList<DayScheduleBean> dayScheduleBeanArrayList;

    @Nullable
    @Override
    public Integer getResLayoutId() {
        return R.layout.fragment_calendar_week;
    }

    @Nullable
    @Override
    public View getResLayoutBinding() {
        setBinding(FragmentCalendarWeekBinding.inflate(getLayoutInflater()));
        return getBinding().getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        switchAndShowView();

        getBinding().customCalendarView.setSelectAddScheduleClick((startTime, endTime) -> {
            scheduleBean.setStartHour(startTime);
            scheduleBean.setEndHour(endTime);
            scheduleBean.setClickDayScheduleStatus(true);
            scheduleBean.setClickWeekScheduleStatus(false);

            startAddSchedulePage();
        });

        getBinding().customWeekView.setSelectAddScheduleClick((startTime, endTime, weekInfo) -> {

            startAddSchedulePage();
        });

    }

    public void startAddSchedulePage() {
        Intent intent = new Intent(getActivity(), AddCalendarScheduleActivity.class);
        Log.d("TAG", "weekFragment scheduleBean :" + scheduleBean.toString());
        intent.putExtra("selectTime", (Parcelable) scheduleBean);
        getActivity().startActivityForResult(intent, 1413);

    }

    public void setCurrentTime(int year, int month, int day) {
        if (scheduleBean == null) {
            scheduleBean = new CalendarScheduleBean(0, 0, 0, 0, 0, 0, 0, 0, 0, -1);
        }

        scheduleBean.setYear(year);
        scheduleBean.setStartMonth(month);
        scheduleBean.setStartDay(day);
        scheduleBean.setEndMonth(month);
        scheduleBean.setEndDay(day);
    }

    public void setScheduleListShow(ArrayList<DayScheduleBean> arrayList) {
        if (arrayList ==null || arrayList.isEmpty()) {
            getBinding().customCalendarView.setCalendarScheduleList(null);
            return;
        }
        this.dayScheduleBeanArrayList = arrayList;

        if (viewType == SCHEDULE_WEEK) {

        } else if (viewType == SCHEDULE_DAY) {
            //获取全部日程信息，渲染到日视图上
            ArrayList<CalendarScheduleViewBean> dayScheduleList = new ArrayList<>();
            CalendarScheduleViewBean scheduleViewBean;

            for (DayScheduleBean itemBean : arrayList) {
                scheduleViewBean = new CalendarScheduleViewBean();
                if (itemBean != null && itemBean.getStartTime() != null) {
                    int startHour = getStartHour(itemBean.getStartTime());
                    scheduleViewBean.setStartHour(startHour);
                }

                if (itemBean != null && itemBean.getEndTime() != null) {
                    int endHour = getEndHour(itemBean.getEndTime());
                    scheduleViewBean.setEndHour(endHour);
                }

                if (itemBean != null && itemBean.getTitle() != null) {
                    scheduleViewBean.setTitle(itemBean.getTitle());
                }

                dayScheduleList.add(scheduleViewBean);
            }

            Log.d("TAG", "请求数据 转换 :" + dayScheduleList);
            getBinding().customCalendarView.setCalendarScheduleList(dayScheduleList);
        }


    }


    /**
     * 设置视图,切换视图
     *
     * @param type 2 周视图 3 日视图
     */
    public void setViewType(int type) {
        this.viewType = type;
        switchAndShowView();
    }

    /**
     * 切换或者显示视图
     */
    private void switchAndShowView() {
        switch (viewType) {
            case SCHEDULE_WEEK:
                getBinding().customWeekView.setVisibility(View.VISIBLE);
                getBinding().customCalendarView.setVisibility(View.GONE);
                break;
            case SCHEDULE_DAY:
                getBinding().customWeekView.setVisibility(View.GONE);
                getBinding().customCalendarView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private int getStartHour(long startTimeMill) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTimeMill);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    private int getEndHour(long endTimeMill) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTimeMill);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


}