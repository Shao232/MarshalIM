package com.marshal.moudle.calendar.schedule.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.google.android.material.tabs.TabLayout;
import com.marshal.base_common.MApplication;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.moudle.calendar.schedule.CalendarAlarmReceive;
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath;
import com.marshal.moudle.calendar.schedule.utils.DateSelectUtils;
import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.databinding.ActivityAddCalendarBinding;

@Route(path = CalendarRouterPath.APP_ADD_EVENT_CALENDAR)
public class CalendarActivity extends BaseViewActivity<ActivityAddCalendarBinding> {


    private CalendarWeekFragment weekFragment = new CalendarWeekFragment();
    private int calendarType = 1;

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

//        ViewModelProvider.Factory factory =
//                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
//                        getInstance(MApplication.Companion.getInstance());
//        viewModel = new ViewModelProvider(this, factory).get(CalendarViewModel.class);


        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(1).setText("月"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(2).setText("周"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(3).setText("日"));


        //设置fragment
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        if (!weekFragment.isAdded()) {
            fragmentManager.add(R.id.fln_show_schedule, weekFragment);
            fragmentManager.commitNowAllowingStateLoss();
        } else {
            fragmentManager.show(weekFragment);
        }
        //fragment设置时间
//        weekFragment.setCurrentTime(currentYear, currentMonth, currentDay);

        //初始化月视图
       getBinding().tabLayout.postDelayed(() -> switchScheduleView(calendarType), 100);

        getBinding().ivAddScheduleShow.setOnClickListener(v -> {

        });

        getBinding().ivShowPopupWindow.setOnClickListener(v -> DateSelectUtils.INSTANCE.showDateSelectDialog(CalendarActivity.this, new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
//                getBinding().calendarView.scrollToCalendar(year, month, day, false, true);
            }
        }));

        getBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getId()) {
                    case 1 -> calendarType = 1;
                    case 2 -> calendarType = 2;
                    case 3 -> calendarType = 3;
                }

                switchScheduleView(calendarType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        addAlarmManager();
    }

    /**
     * 添加闹钟功能
     */
    private void addAlarmManager() {
        //todo test
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(2023, 9, 19, 10, 4);
        Intent intent = new Intent(this, CalendarAlarmReceive.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("TAG", "设置闹钟时间戳 :" + calendar.getTimeInMillis());
    }

    @Override
    public void subscribeBack() {
        super.subscribeBack();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            weekFragment.updateScheduleList();

        }
    }

    /**
     * 切换视图
     *
     * @param type      1 月视图 2 周视图 3 日视图
     */
    private void switchScheduleView(int type) {
        weekFragment.setViewType(type);
    }

}