package com.marshal.calendar;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.google.android.material.tabs.TabLayout;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.marshal.AppRouterPath;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.databinding.ActivityAddCalendarBinding;

@Route(path = AppRouterPath.APP_ADD_EVENT_CALENDAR)
public class CalendarActivity extends BaseViewActivity<ActivityAddCalendarBinding> {




    private CalendarView.OnCalendarSelectListener onCalendarSelectListener = new CalendarView.OnCalendarSelectListener() {
        @Override
        public void onCalendarOutOfRange(Calendar calendar) {

        }

        @Override
        public void onCalendarSelect(Calendar calendar, boolean isClick) {
            getBinding().tvYearMonth.setText(calendar.getYear() + "年" +calendar.getMonth() + "月");
        }
    };


    @Override
    public boolean hasToolbar() {
        return true;
    }

    @Nullable
    @Override
    public View getResLayoutBinding() {
        setBinding(ActivityAddCalendarBinding.inflate(getLayoutInflater()));
        return getBinding().getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {

        if (getHasIncludeToolbar()) {
            setTitle("显示日历");
        }

        getBinding().tvYearMonth.setText(getBinding().calendarView.getCurYear() + "年" + getBinding().calendarView.getCurMonth() + "月");

        getBinding().calendarView.setOnCalendarSelectListener(onCalendarSelectListener);

        getBinding().ivShowPopupWindow.setOnClickListener(v -> DateSelectUtils.INSTANCE.showDateSelectDialog(CalendarActivity.this, new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {

                getBinding().calendarView.scrollToCalendar(year, month, day, false, true);
            }
        }));

        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(1).setText("月"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(2).setText("周"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(3).setText("日"));
        getBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getId()) {
                    case 1:
                        switchScheduleView(true);
                        break;
                    case 2:
                    case 3:
                        switchScheduleView(false);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void switchScheduleView(boolean showMonth) {
        if (showMonth) {
            getBinding().nestedScrollView.setVisibility(View.VISIBLE);
            getBinding().flnShowSchedule.setVisibility(View.GONE);
            getBinding().calendarLayout.setModeBothMonthWeekView();
            getBinding().calendarLayout.expand(150);
        } else {
            getBinding().nestedScrollView.setVisibility(View.GONE);
            getBinding().flnShowSchedule.setVisibility(View.VISIBLE);
            getBinding().calendarLayout.setModeOnlyMonthView();
            getBinding().calendarLayout.shrink(150);
        }
    }

}