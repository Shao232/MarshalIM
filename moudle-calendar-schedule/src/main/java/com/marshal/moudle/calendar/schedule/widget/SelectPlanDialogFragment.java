package com.marshal.moudle.calendar.schedule.widget;

import android.view.View;

import androidx.annotation.Nullable;

import com.marshal.base_common.basedialog.BaseBottomSheetDialogFragment;
import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.databinding.DialogSelectPlanBinding;

public class SelectPlanDialogFragment extends BaseBottomSheetDialogFragment<DialogSelectPlanBinding> {

    @Override
    public int getResLayoutId() {
        return R.layout.dialog_select_plan;
    }

    @Nullable
    @Override
    public View getResLayoutBinding() {
        setBinding(DialogSelectPlanBinding.inflate(getLayoutInflater()));
        return getBinding().getRoot();
    }

    @Override
    public void initView() {



    }



}
