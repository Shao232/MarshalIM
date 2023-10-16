package com.marshal.calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.google.android.material.tabs.TabLayout;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.marshal.AppRouterPath;
import com.marshal.R;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.databinding.ActivityAddCalendarBinding;

@Route(path = AppRouterPath.APP_ADD_EVENT_CALENDAR)
public class CalendarActivity extends BaseViewActivity<ActivityAddCalendarBinding> {

    private CalendarWeekFragment weekFragment = new CalendarWeekFragment();

    private CalendarView.OnCalendarSelectListener onCalendarSelectListener = new CalendarView.OnCalendarSelectListener() {
        @Override
        public void onCalendarOutOfRange(Calendar calendar) {
        }

        @Override
        public void onCalendarSelect(Calendar calendar, boolean isClick) {
            getBinding().tvYearMonth.setText(calendar.getYear() + "年" +calendar.getMonth() + "月");
            Log.d("TAG","日期:"+calendar.getYear()+", "+calendar.getMonth()+", "+calendar.getDay());
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

        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(1).setText("月"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(2).setText("周"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(3).setText("日"));
        getBinding().tvYearMonth.setText(getBinding().calendarView.getCurYear() + "年" + getBinding().calendarView.getCurMonth() + "月");
        getBinding().calendarView.setOnCalendarSelectListener(onCalendarSelectListener);

        //设置fragment
        FragmentTransaction fragmentManager =  getSupportFragmentManager().beginTransaction();
        if(!weekFragment.isAdded()) {
            fragmentManager.add(R.id.fln_show_schedule, weekFragment);
            fragmentManager.commitNowAllowingStateLoss();
        }else {
            fragmentManager.show(weekFragment);
        }


        getBinding().ivShowPopupWindow.setOnClickListener(v -> DateSelectUtils.INSTANCE.showDateSelectDialog(CalendarActivity.this, new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
                getBinding().calendarView.scrollToCalendar(year, month, day, false, true);
            }
        }));

        getBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getId()) {
                    case 1:
                        switchScheduleView(true,1);
                        break;
                    case 2:
                        switchScheduleView(false,2);
                        break;
                    case 3:
                        switchScheduleView(false,3);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode ==1413) {
                String title = data.getStringExtra("title");
                Log.d("TAG", "activity title: " + title);
                weekFragment.setScheduleContent(title,3);
            }

            if (requestCode ==1414) {
                String title = data.getStringExtra("title");
                Log.d("TAG", "activity title: " + title);
                weekFragment.setScheduleContent(title,2);
            }
        }

    }

    /**
     * 切换视图
     * @param showMonth
     * @param type   1 月视图 2 周视图 3 日视图
     */
    private void switchScheduleView(boolean showMonth,int type) {
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

        weekFragment.setViewType(type);

    }

}