package com.marshal.moudle.calendar.schedule.ui.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder;
import com.marshal.moudle.calendar.schedule.R;

public class SelectContentViewHolder extends BaseRecyclerViewHolder{

    public TextView tvSelectPlanItem;

    public SelectContentViewHolder(@NonNull ViewGroup parent) {
        super(R.layout.item_select_plan, parent);
        tvSelectPlanItem = itemView.findViewById(R.id.tv_select_plan_dialog_item);
    }


}

