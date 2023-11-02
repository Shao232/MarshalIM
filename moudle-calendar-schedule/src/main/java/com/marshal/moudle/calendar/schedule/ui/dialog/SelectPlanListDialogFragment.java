package com.marshal.moudle.calendar.schedule.ui.dialog;

import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.ui.adapter.SelectPlanAdapter;

public class SelectPlanListDialogFragment extends BaseSelectListDialogFragment<SelectPlanAdapter,PlanBean> {

    @Override
    protected String getDialogTitle() {
        return "选择生涯规划";
    }

    @Override
    protected SelectPlanAdapter getDataAdapter() {
        return new SelectPlanAdapter();
    }
}
