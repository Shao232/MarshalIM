package com.marshal.calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.marshal.AppRouterPath;
import com.marshal.R;
import com.marshal.base_common.baseview.BaseViewActivity;
import com.marshal.databinding.ActivityAddCalendarScheduleBinding;

@Route(path = AppRouterPath.APP_ADD_CALENDAR_SCHEDULE)
public class AddCalendarScheduleActivity extends BaseViewActivity<ActivityAddCalendarScheduleBinding> {

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
        if(getHasIncludeToolbar()) {
            setTitle("添加日程");
        }

        getBinding().tvCommitSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getBinding().editTitleSchedule.getText().toString().trim();
                if(title.isEmpty()) {
                    showToast("请添加标题");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("title",title);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }
}