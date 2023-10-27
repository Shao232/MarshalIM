package com.marshal.moudle.calendar.schedule.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marshal.base_common.baseadapter.AdapterItemOnClickListener;
import com.marshal.base_common.basedialog.BaseBottomSheetDialogFragment;
import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.databinding.DialogSelectPlanBinding;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;
import com.marshal.moudle.calendar.schedule.ui.adapter.SelectPlanAdapter;

import java.util.ArrayList;

public class SelectPlanDialogFragment extends BaseBottomSheetDialogFragment<DialogSelectPlanBinding> {

    private ArrayList<PlanBean> planBeanArrayList;
    private SelectPlanAdapter adapter;
    private SelectPlanDialogListener selectPlanDialogListener;

    @Override
    public int getDialogTheme() {
        return com.marshal.base_common.R.style.BottomDialog;
    }

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

        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new SelectPlanAdapter();
        if(planBeanArrayList!=null && !planBeanArrayList.isEmpty()) {
            adapter.addListAll(planBeanArrayList);
        }
        getBinding().recyclerView.setAdapter(adapter);
        adapter.setAdapterItemOnClickListener(new AdapterItemOnClickListener<>() {
            @Override
            public void onClick(@NonNull View view, int position) {
               dismiss();
            }

            @Override
            public void onClick(@NonNull View view, PlanBean bean) {
                if (selectPlanDialogListener != null) {
                    selectPlanDialogListener.onSelectPlanDialogListener(bean);
                }
                dismiss();
            }
        });
    }

    public SelectPlanDialogFragment setPlanList(ArrayList<PlanBean> arrayList){
        this.planBeanArrayList = arrayList;
        return this;
    }

    public void setSelectPlanDialogListener(SelectPlanDialogListener selectPlanDialogListener) {
        this.selectPlanDialogListener = selectPlanDialogListener;
    }

    public interface SelectPlanDialogListener{
        void onSelectPlanDialogListener(PlanBean bean);
    }

}
