package com.marshal.moudle.calendar.schedule.ui.dialog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marshal.base_common.baseadapter.AdapterItemOnClickListener;
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter;
import com.marshal.base_common.basedialog.BaseBottomSheetDialogFragment;
import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.databinding.DialogSelectListBinding;

import java.util.ArrayList;

public abstract class BaseSelectListDialogFragment<T extends BaseRecyclerAdapter, E> extends BaseBottomSheetDialogFragment<DialogSelectListBinding> {

    private SelectListDialogListener selectListDialogListener;
    protected T adapter;
    private ArrayList<E> dataList;

    @Override
    public int getDialogTheme() {
        return com.marshal.base_common.R.style.BottomDialog;
    }

    @Override
    public int getResLayoutId() {
        return R.layout.dialog_select_list;
    }

    @Nullable
    @Override
    public View getResLayoutBinding() {
        setBinding(DialogSelectListBinding.inflate(getLayoutInflater()));
        return getBinding().getRoot();
    }

    @Override
    public void initView() {

        getBinding().tvShowTitleList.setText(getDialogTitle());
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = getDataAdapter();
        getBinding().recyclerView.setAdapter(adapter);

        if (adapter != null && dataList!=null && !dataList.isEmpty()) {
            adapter.addListAll(dataList);
            adapter.notifyDataSetChanged();
        }

        adapter.setAdapterItemOnClickListener(new AdapterItemOnClickListener<E>() {
            @Override
            public void onClick(@NonNull View view, int position) {
                if (selectListDialogListener != null) {
                    selectListDialogListener.onSelectListDialogListener(position);
                }
                dismiss();
            }

            @Override
            public void onClick(@NonNull View view, E bean) {
            }
        });
    }

    protected abstract String getDialogTitle();

    protected abstract T getDataAdapter();

    public void setSelectListDialogListener(SelectListDialogListener selectPlanDialogListener) {
        this.selectListDialogListener = selectPlanDialogListener;
    }

    public void setDataList(ArrayList<E> dataList) {
        this.dataList = dataList;
    }

    public ArrayList<E> getDataList() {
        if (adapter != null) {
            return adapter.getItemList();
        } else {
            return dataList;
        }
    }


    public interface SelectListDialogListener {
        void onSelectListDialogListener(int position);
    }

}
