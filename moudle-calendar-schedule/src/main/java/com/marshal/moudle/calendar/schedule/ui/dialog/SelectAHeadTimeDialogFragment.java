package com.marshal.moudle.calendar.schedule.ui.dialog;
import com.marshal.moudle.calendar.schedule.pojo.AHeadTimeBean;
import com.marshal.moudle.calendar.schedule.ui.adapter.SelectAHeadTimeAdapter;

public class SelectAHeadTimeDialogFragment extends BaseSelectListDialogFragment<SelectAHeadTimeAdapter, AHeadTimeBean> {

    @Override
    protected String getDialogTitle() {
        return "选择提醒时间";
    }

    @Override
    protected SelectAHeadTimeAdapter getDataAdapter() {
        return new SelectAHeadTimeAdapter();
    }
}
