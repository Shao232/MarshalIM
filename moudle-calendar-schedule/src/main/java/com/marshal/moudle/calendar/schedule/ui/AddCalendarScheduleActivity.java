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
import com.marshal.base_common.MApplication;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.moudle.calendar.schedule.StoreCalendarDataKt;
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleBean;
import com.marshal.moudle.calendar.schedule.ui.viewmodel.AddScheduleViewModel;
import com.marshal.moudle.calendar.schedule.ui.viewmodel.CalendarViewModel;
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath;
import com.marshal.moudle.calendar.schedule.databinding.ActivityAddCalendarScheduleBinding;

@Route(path = CalendarRouterPath.APP_ADD_CALENDAR_SCHEDULE)
public class AddCalendarScheduleActivity extends BaseViewActivity<ActivityAddCalendarScheduleBinding> {

    private AddScheduleViewModel viewModel;
    private CalendarScheduleBean scheduleBean;

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

        if ( getIntent().getParcelableExtra("selectTime")!=null) {
           scheduleBean =  getIntent().getParcelableExtra("selectTime");
        }

        Log.d("TAG","data :"+scheduleBean.toString());

        ViewModelProvider.Factory factory =
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                        getInstance(MApplication.Companion.getInstance());
        viewModel = new ViewModelProvider(this, factory).get(AddScheduleViewModel.class);

        viewModel.getPlanList();

        viewModel.responseResult.observe(this, aBoolean -> {
            if (!aBoolean) {
                if (StoreCalendarDataKt.getAppInfoToken().isEmpty()) {
                    showToast("请重新登录");
                }
            }
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
}