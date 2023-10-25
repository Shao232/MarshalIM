package com.marshal.moudle.calendar.schedule;

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
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.https.HttpRequestFactory;
import com.marshal.https.ScheduleService;
import com.marshal.moudle.calendar.schedule.databinding.ActivityAddCalendarBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Route(path = CalendarRouterPath.APP_ADD_EVENT_CALENDAR)
public class CalendarActivity extends BaseViewActivity<ActivityAddCalendarBinding> {

    /**
     * 当前日历对应的时间戳
     */
    private long curTimeMillis;


    private CalendarWeekFragment weekFragment = new CalendarWeekFragment();

    private CalendarView.OnCalendarSelectListener onCalendarSelectListener = new CalendarView.OnCalendarSelectListener() {
        @Override
        public void onCalendarOutOfRange(Calendar calendar) {
        }

        @Override
        public void onCalendarSelect(Calendar calendar, boolean isClick) {
            curTimeMillis = calendar.getTimeInMillis();
            getBinding().tvYearMonth.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
            Log.d("TAG", "日期:" + calendar.getYear() + ", " + calendar.getMonth() + ", " + calendar.getDay());

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

        curTimeMillis = getBinding().calendarView.getSelectedCalendar().getTimeInMillis();

        //设置fragment
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        if (!weekFragment.isAdded()) {
            fragmentManager.add(R.id.fln_show_schedule, weekFragment);
            fragmentManager.commitNowAllowingStateLoss();
        } else {
            fragmentManager.show(weekFragment);
        }

        getBinding().ivAddScheduleShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点击加号");
            }
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

        getScheduleData();

        addAlarmManager();
    }

    /**
     * 添加闹钟功能
     */
    private void addAlarmManager() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(2023, 9, 19, 10, 4);
        Intent intent = new Intent(this, CalendarAlarmReceive.class);
        PendingIntent pendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("TAG", "设置闹钟时间戳 :" + calendar.getTimeInMillis());
    }

    private void getScheduleData() {
        //请求每日日程信息
        Retrofit retrofit = HttpRequestFactory.INSTANCE.getScheduleRequest();
        if (retrofit != null) {
            ScheduleService service = retrofit.create(ScheduleService.class);
            Log.d("TAG", "当前时间戳 :" + curTimeMillis);
            service.getScheduleDailyList(String.valueOf(curTimeMillis)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("TAG", "response : " + response.body());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("TAG", "错误 :" + t.getMessage());
                }
            });
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1413) {
                String title = data.getStringExtra("title");
                Log.d("TAG", "activity title: " + title);
                weekFragment.setScheduleContent(title, 3);
            }

            if (requestCode == 1414) {
                String title = data.getStringExtra("title");
                Log.d("TAG", "activity title: " + title);
                weekFragment.setScheduleContent(title, 2);
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

    }

}