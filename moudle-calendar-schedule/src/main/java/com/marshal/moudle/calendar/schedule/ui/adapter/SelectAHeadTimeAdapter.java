package com.marshal.moudle.calendar.schedule.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marshal.base_common.baseadapter.BaseRecyclerAdapter;
import com.marshal.moudle.calendar.schedule.pojo.AHeadTimeBean;
import com.marshal.moudle.calendar.schedule.pojo.PlanBean;

public class SelectAHeadTimeAdapter extends BaseRecyclerAdapter<SelectContentViewHolder, AHeadTimeBean> {

    @NonNull
    @Override
    public SelectContentViewHolder onViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectContentViewHolder(parent);
    }

    @Override
    public void bindViewHolderData(@NonNull SelectContentViewHolder holder, int position) {
        AHeadTimeBean item = getItemList().get(position);
        holder.tvSelectPlanItem.setText(item.getTitle());
    }
}
