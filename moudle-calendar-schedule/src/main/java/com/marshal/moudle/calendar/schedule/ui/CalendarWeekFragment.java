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
import com.marshal.moudle.calendar.schedule.ui.AddCalendarScheduleActivity;
import com.marshal.moudle.calendar.schedule.widget.CustomDayCalendarView;

public class CalendarWeekFragment extends BaseViewFragment<FragmentCalendarWeekBinding> {

    //视图类型
    private int viewType = 1;

    private CalendarScheduleBean scheduleBean;

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

            startAddSchedulePage();
        });

        getBinding().customWeekView.setSelectAddScheduleClick((startTime, endTime, weekInfo) -> {
            startAddSchedulePage();
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1413 && resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra("title");
            Log.d("TAG", "title: " + title);
        }
    }

    public void startAddSchedulePage() {
        Intent intent = new Intent(getActivity(), AddCalendarScheduleActivity.class);
        Log.d("TAG","weekFragment scheduleBean :"+scheduleBean.toString());
        intent.putExtra("selectTime", (Parcelable) scheduleBean);

        getActivity().startActivityForResult(intent, 1413);

    }

    public void setCurrentTime(int year, int month, int day) {
        if (scheduleBean == null) {
            scheduleBean = new CalendarScheduleBean(0, 0, 0,0, 0,0, 0,0,0, -1);
        }

        scheduleBean.setYear(year);
        scheduleBean.setStartMonth(month);
        scheduleBean.setStartDay(day);
        scheduleBean.setEndMonth(month);
        scheduleBean.setEndDay(day);
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
            case 2:
                getBinding().customWeekView.setVisibility(View.VISIBLE);
                getBinding().customCalendarView.setVisibility(View.GONE);
                break;
            case 3:
                getBinding().customWeekView.setVisibility(View.GONE);
                getBinding().customCalendarView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void setScheduleContent(String title, int type) {
        if (type == 2) {
            getBinding().customWeekView.setScheduleContent(title);
        } else {
            getBinding().customCalendarView.setScheduleContent(title);
        }
    }

}