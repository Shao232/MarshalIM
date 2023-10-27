package com.marshal.moudle.calendar.schedule.ui.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder;
import com.marshal.moudle.calendar.schedule.R;

public class SelectPlanViewHolder extends BaseRecyclerViewHolder{

    public TextView tvSelectPlanItem;

    public SelectPlanViewHolder(@NonNull ViewGroup parent) {
        super(R.layout.item_select_plan, parent);
        tvSelectPlanItem = itemView.findViewById(R.id.tv_select_plan_dialog_item);
    }


}

