package com.marshal.moudle.calendar.schedule.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.haibin.calendarview.CalendarUtil;
import com.marshal.base_common.MApplication;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleBean;
import com.marshal.moudle.calendar.schedule.ui.viewmodel.AddScheduleViewModel;
import com.marshal.moudle.calendar.schedule.ui.viewmodel.CalendarViewModel;
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath;
import com.marshal.moudle.calendar.schedule.databinding.ActivityAddCalendarScheduleBinding;
import com.marshal.moudle.calendar.schedule.utils.CalendarScheduleUtils;
import com.marshal.moudle.calendar.schedule.utils.DateSelectUtils;
import com.marshal.moudle.calendar.schedule.widget.SelectPlanDialogFragment;

@Route(path = CalendarRouterPath.APP_ADD_CALENDAR_SCHEDULE)
public class AddCalendarScheduleActivity extends BaseViewActivity<ActivityAddCalendarScheduleBinding> {

    private AddScheduleViewModel viewModel;
    private CalendarScheduleBean scheduleBean;
    private SelectPlanDialogFragment dialogFragment;

    @Override
    public boolean hasToolbar() {
        return true;
    }

    @Nullable
    @Override
    public View getResLayoutBinding() {
        setBinding(ActivityAddCalendarScheduleBinding.inflate(getLayoutInflater()));
        return getBinding().getRoot();
    }

    @Override
    public void initView() {
        if (getHasIncludeToolbar()) {
            setTitle("添加日程");
        }

        if (getIntent().getParcelableExtra("selectTime") != null) {
            scheduleBean = getIntent().getParcelableExtra("selectTime");
            Log.d("TAG", "data :" + scheduleBean.toString());
            setStartTimeContent();
            setEndTimeContent();

        }

        ViewModelProvider.Factory factory =
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                        getInstance(MApplication.Companion.getInstance());
        viewModel = new ViewModelProvider(this, factory).get(AddScheduleViewModel.class);

        dialogFragment = new SelectPlanDialogFragment();

        viewModel.getPlanList();

        viewModel.responseResult.observe(this, aBoolean -> {
            if (!aBoolean) {
                if (StoreCalendarDataKt.getAppInfoToken().isEmpty()) {
                    showToast("请重新登录");
                }
            }
        });

        viewModel.resultListData.observe(this,arrayList ->{
            Log.d("TAG","planList :"+arrayList.size());

        });


        getBinding().tvStartTimeContentSchedule.setOnClickListener(v -> {
            DateSelectUtils.INSTANCE.showDateSelectMonthDayDialog(this,
                    scheduleBean.getYear(), scheduleBean.getStartMonth(), scheduleBean.getStartDay(), scheduleBean.getStartHour(),
                    (year, month, day, hour, minute, second) -> {

                        if (month > scheduleBean.getEndMonth() ||
                                day > scheduleBean.getEndDay() ||
                                hour > scheduleBean.getEndHour() ||
                                minute > scheduleBean.getEndMinute()) {
                            showToast("请重新选择开始时间,开始时间不能大于结束时间");
                            return;
                        }

                        scheduleBean.setStartMonth(month)
                                .setStartDay(day)
                                .setStartHour(hour)
                                .setStartMinute(minute);
                        setStartTimeContent();
                    });
        });

        getBinding().tvEndTimeContentSchedule.setOnClickListener(v -> {
            DateSelectUtils.INSTANCE.showDateSelectMonthDayDialog(this,
                    scheduleBean.getYear(), scheduleBean.getEndMonth(), scheduleBean.getEndDay(), scheduleBean.getStartHour(),
                    (year, month, day, hour, minute, second) -> {
                        if (scheduleBean.getStartMonth() > month ||
                                scheduleBean.getStartDay() > day ||
                                scheduleBean.getStartHour() > hour ||
                                scheduleBean.getStartMinute() > minute) {
                            showToast("请重新选择结束时间,结束时间不能小于开始时间");
                            return;
                        }

                        scheduleBean.setEndMonth(month)
                                .setEndDay(day)
                                .setEndHour(hour)
                                .setEndMinute(minute);
                        setEndTimeContent();
                    });
        });


        getBinding().tvCommitSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getBinding().editTitleSchedule.getText().toString().trim();
                if (title.isEmpty()) {
                    showToast("请添加标题");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("title", title);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void setStartTimeContent() {
        if (scheduleBean.getStartHour() == 0 || scheduleBean.getStartMinute() == 0
                || scheduleBean.getEndHour() == 0 || scheduleBean.getEndMinute() == 0) {
            DatimeEntity timeEntity = DatimeEntity.now();
            scheduleBean.setStartHour(timeEntity.getTime().getHour());
            scheduleBean.setEndHour(timeEntity.getTime().getHour());
        }

        String startTimeStr = scheduleBean.getStartMonth() + "月" + scheduleBean.getStartDay() + "日" + " "
                + startHour2Str(scheduleBean.getStartHour(), scheduleBean.getStartMinute());
        getBinding().tvStartTimeContentSchedule.setText(startTimeStr);
    }

    private void setEndTimeContent() {
        String endTimeStr = scheduleBean.getEndMonth() + "月" + scheduleBean.getEndDay() + "日" + " "
                + startHour2Str(scheduleBean.getEndHour(), scheduleBean.getEndMinute());
        getBinding().tvEndTimeContentSchedule.setText(endTimeStr);
    }

    private String startHour2Str(int startHour, int minute) {
        if (startHour < 10) {
            return "0" + startHour + ":" + (minute < 10 ? "0" + minute : minute);
        } else {
            return startHour + ":" + (minute < 10 ? "0" + minute : minute);
        }
    }

    private String endHour2Str(int endHour, int minute) {
        if (endHour < 10) {
            return "0" + endHour + ":" + (minute < 10 ? "0" + minute : minute);
        } else {
            return endHour + ":" + (minute < 10 ? "0" + minute : minute);
        }
    }

}