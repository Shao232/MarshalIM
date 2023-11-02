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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.google.android.material.tabs.TabLayout;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.marshal.base_common.MApplication;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.moudle.calendar.schedule.CalendarAlarmReceive;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.pojo.DayScheduleBean;
import com.marshal.moudle.calendar.schedule.ui.viewmodel.CalendarViewModel;
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath;
import com.marshal.moudle.calendar.schedule.utils.DateSelectUtils;
import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.databinding.ActivityAddCalendarBinding;

import java.util.ArrayList;
import java.util.Objects;

@Route(path = CalendarRouterPath.APP_ADD_EVENT_CALENDAR)
public class CalendarActivity extends BaseViewActivity<ActivityAddCalendarBinding> {

    /**
     * 当前日历对应的时间戳
     */
    private long curTimeMillis;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private ArrayList<DayScheduleBean> dayScheduleBeanArrayList;

    private CalendarViewModel viewModel;

    private CalendarWeekFragment weekFragment = new CalendarWeekFragment();

    private CalendarView.OnCalendarSelectListener onCalendarSelectListener = new CalendarView.OnCalendarSelectListener() {
        @Override
        public void onCalendarOutOfRange(Calendar calendar) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onCalendarSelect(Calendar calendar, boolean isClick) {
            curTimeMillis = calendar.getTimeInMillis();
            currentYear = calendar.getYear();
            currentMonth = calendar.getMonth();
            currentDay = calendar.getDay();
            getBinding().tvYearMonth.setText(currentYear + "年" +currentMonth + "月");
            Log.d("TAG", "日期:" + currentYear + ", " + currentMonth + ", " +currentDay);
            weekFragment.setCurrentTime(currentYear,currentMonth,currentDay);
            //切换日期，刷新数据
            if(curTimeMillis !=0) {
                viewModel.getScheduleData(String.valueOf(curTimeMillis));
            }
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

        ViewModelProvider.Factory factory =
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                        getInstance(MApplication.Companion.getInstance());
        viewModel = new ViewModelProvider(this, factory).get(CalendarViewModel.class);


        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(1).setText("月"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(2).setText("周"));
        getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setId(3).setText("日"));

        currentYear = getBinding().calendarView.getCurYear();
        currentMonth = getBinding().calendarView.getCurMonth();
        currentDay = getBinding().calendarView.getCurDay();
        getBinding().tvYearMonth.setText(currentYear + "年" + currentMonth + "月");

        getBinding().calendarView.setOnCalendarSelectListener(onCalendarSelectListener);

        curTimeMillis = getBinding().calendarView.getSelectedCalendar().getTimeInMillis();

        //设置fragment
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        if (!weekFragment.isAdded()) {
            fragmentManager.add(R.id.fln_show_schedule, weekFragment);
            fragmentManager.commitNowAllowingStateLoss();
        } else {
            fragmentManager.show(weekFragment);
        }
        //fragment设置时间
        weekFragment.setCurrentTime(currentYear,currentMonth,currentDay);

        getBinding().ivAddScheduleShow.setOnClickListener(v -> {
           weekFragment.startAddSchedulePage();
        });

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
                        switchScheduleView(true, 1);
                        break;
                    case 2:
                        switchScheduleView(false, 2);
                        break;
                    case 3:
                        switchScheduleView(false, 3);
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
        Log.d("TAG", "当前时间戳 :" + curTimeMillis);
        viewModel.getScheduleData(String.valueOf(curTimeMillis));

        viewModel.getResponseResult().observe(this, aBoolean -> {
            if(!aBoolean) {
                if (StoreCalendarDataKt.getAppInfoToken().isEmpty()) {
                    showToast("请重新登录");
                }
            }
        });

        viewModel.getErrorData().observe(this,throwable -> {
            if(throwable.getMessage() !=null) {
                showToast(throwable.getMessage());
            }
        });

        viewModel.responseScheduleLiveData.observe(this,arrayList ->{
                //回调fragment渲染自定义view
                dayScheduleBeanArrayList = arrayList;
                weekFragment.setScheduleListShow(dayScheduleBeanArrayList);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(curTimeMillis !=0) {
                viewModel.getScheduleData(String.valueOf(curTimeMillis));
            }
        }
    }

    /**
     * 切换视图
     *
     * @param showMonth
     * @param type      1 月视图 2 周视图 3 日视图
     */
    private void switchScheduleView(boolean showMonth, int type) {
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
        weekFragment.setScheduleListShow(dayScheduleBeanArrayList);

    }

}