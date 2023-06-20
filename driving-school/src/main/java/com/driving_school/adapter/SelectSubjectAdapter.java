//package com.driving_school.adapter;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.driving_school.R;
//import com.driving_school.activity.base.BaseAdapter;
//import com.driving_school.bean.SelectSubjectBean;
//import com.driving_school.utils.MyLog;
//import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder;
//
///**
// * Created by 11470 on 2017/10/26.
// */
//
//public class SelectSubjectAdapter extends BaseAdapter<SelectSubjectAdapter.SelectSubjectViewHolder, SelectSubjectBean> {
//
//
//    public SelectSubjectAdapter(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected int getLayout() {
//        return R.layout.item_select_subject;
//    }
//
//    @Override
//    public SelectSubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return super.onCreateViewHolder(parent, viewType);
//    }
//
//    @Override
//    protected SelectSubjectViewHolder getViewHolder(View view) {
//        return new SelectSubjectViewHolder(view.getContext(), R.layout.item_select_subject,null);
//    }
//
//    @Override
//    protected void onBindData(SelectSubjectViewHolder holder, SelectSubjectBean bean, int position) {
//        holder.tvSubject.setText(bean.getSubject());
//        switch (bean.getSelectStatus()){
//            case 1:
//                holder.tvSubject.setTextColor(mContext.getResources().getColor(R.color.color_048ae9));
//                holder.tvSubject.setBackgroundResource(R.drawable.ok_select_img);
//                break;
//            case 2:
//                holder.tvSubject.setTextColor(mContext.getResources().getColor(R.color.color_f4011f));
//                holder.tvSubject.setBackgroundResource(R.drawable.error_select_img);
//                break;
//            case 0:
//                holder.tvSubject.setTextColor(mContext.getResources().getColor(R.color.color_9d9d9d));
//                holder.tvSubject.setBackgroundResource(R.drawable.default_select_img);
//                break;
//        }
//        holder.listener.setData(bean,position);
//    }
//
//     class SelectSubjectViewHolder extends BaseRecyclerViewHolder {
//        TextView tvSubject;
//        MyClickListener listener;
//
//        SelectSubjectViewHolder(Context context, int resId, ViewGroup parent) {
//            super(context,resId,parent);
//            tvSubject = itemView.findViewById(R.id.tv_subject_item);
//            listener = new MyClickListener();
//            itemView.setOnClickListener(listener);
//        }
//    }
//
//    private class MyClickListener implements View.OnClickListener{
//
//        private SelectSubjectBean data;
//        private int position;
//
//        private void setData(SelectSubjectBean data,int position){
//            this.data = data;
//            this.position = position;
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (onItemClickListener!=null){
//                MyLog.i("Select","BaseAdapter点击的题目>>>>"+position);
//                onItemClickListener.onItemClickListener(data,position);
//            }
//        }
//    }
//
//}
