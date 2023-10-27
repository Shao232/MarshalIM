package com.marshal.moudle.calendar.schedule.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.marshal.base_common.MApplication;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleBean;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.ui.viewmodel.AddScheduleViewModel;
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath;
import com.marshal.moudle.calendar.schedule.databinding.ActivityAddCalendarScheduleBinding;
import com.marshal.moudle.calendar.schedule.utils.DateSelectUtils;

import java.util.Calendar;


@Route(path = CalendarRouterPath.APP_ADD_CALENDAR_SCHEDULE)
public class AddCalendarScheduleActivity extends BaseViewActivity<ActivityAddCalendarScheduleBinding> {

    private AddScheduleViewModel viewModel;
    private CalendarScheduleBean scheduleBean;
    private SelectPlanDialogFragment dialogFragment;
    private PlanBean selectedPlanBean;

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
            DatimeEntity timeEntity = DatimeEntity.now();
            //设置日视图状态下如果点击时间段跳转就不处理，如果不是采取当前时间显示
            if (scheduleBean.getStartHour() == 0 && !scheduleBean.isClickDayScheduleStatus()) {
                scheduleBean.setStartHour(timeEntity.getTime().getHour());
                scheduleBean.setEndHour(timeEntity.getTime().getHour());
            }

            setStartTimeContent();
            setEndTimeContent();
        }

        ViewModelProvider.Factory factory =
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                        getInstance(MApplication.Companion.getInstance());
        viewModel = new ViewModelProvider(this, factory).get(AddScheduleViewModel.class);

        if (dialogFragment == null) {
            dialogFragment = new SelectPlanDialogFragment();
        }



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

        getBinding().lvnSelectPlanSchedule.setOnClickListener(v -> {
            if (!dialogFragment.isAdded()) {
                dialogFragment.setSelectPlanDialogListener(bean -> {
                    selectedPlanBean = bean;
                    getBinding().tvSelectPlanContentSchedule.setText(bean.getTitle());

                });
                dialogFragment.show(getSupportFragmentManager(), "select_plan_dialog");
            }
        });


        getBinding().tvCommitSchedule.setOnClickListener(v -> {
            String title = getBinding().editTitleSchedule.getText().toString().trim();
            String content = getBinding().editContentSchedule.getText().toString().trim();
            if (title.isEmpty()) {
                showToast("请添加标题");
                return;
            }

            if (content.isEmpty()) {
                showToast("请添加内容");
                return;
            }

            if (selectedPlanBean == null) {
                showToast("请选择规划");
                return;
            }

            Calendar startTime = Calendar.getInstance();
            startTime.set(scheduleBean.getYear(), scheduleBean.getStartMonth()-1, scheduleBean.getStartDay()
                    , scheduleBean.getStartHour(), scheduleBean.getStartMinute(), 0);
            Calendar endTime = Calendar.getInstance();
            endTime.set(scheduleBean.getYear(), scheduleBean.getEndMonth()-1, scheduleBean.getEndDay()
                    , scheduleBean.getEndHour(), scheduleBean.getEndMinute(), 0);

            viewModel.postScheduleAdd(title, content, startTime.getTimeInMillis(),endTime.getTimeInMillis(),
                    selectedPlanBean.getId().toString(),selectedPlanBean.getTitle());

        });

    }

    @Override
    public void subscribeBack() {
        super.subscribeBack();
        viewModel.getPlanList();

        viewModel.responseResult.observe(this, aBoolean -> {
            if (!aBoolean) {
                if (StoreCalendarDataKt.getAppInfoToken().isEmpty()) {
                    showToast("请重新登录");
                }
            }
        });

        viewModel.resultListData.observe(this, arrayList -> {
            Log.d("TAG", "planList :" + arrayList.size());
            dialogFragment.setPlanList(arrayList);
        });

        viewModel.responseAddSchedule.observe(this,postResult ->{
            if(postResult) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void setStartTimeContent() {

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