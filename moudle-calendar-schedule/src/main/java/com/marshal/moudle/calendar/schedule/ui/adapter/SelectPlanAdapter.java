package com.marshal.moudle.calendar.schedule.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marshal.base_common.baseadapter.BaseRecyclerAdapter;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;

public class SelectPlanAdapter extends BaseRecyclerAdapter<SelectPlanViewHolder, PlanBean> {

    @NonNull
    @Override
    public SelectPlanViewHolder onViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectPlanViewHolder(parent);
    }

    @Override
    public void bindViewHolderData(@NonNull SelectPlanViewHolder holder, int position) {
        PlanBean item = getItemList().get(position);
        holder.tvSelectPlanItem.setText(item.getTitle());

    }


}
