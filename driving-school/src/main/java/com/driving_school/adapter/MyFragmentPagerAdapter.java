//package com.driving_school.adapter;
//
//import android.util.Log;
//
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.viewpager.widget.PagerAdapter;
//
//import com.driving_school.activity.base.BaseFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public  class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
//
//    private List<BaseFragment> fragments;
//    private FragmentManager mFragmentManager;
//
//    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
//        super(fm);
//        this.fragments = fragments;
//        this.mFragmentManager=fm;
//    }
//
//    @Override
//    public BaseFragment getItem(int position) {
//        return fragments.get(position);
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }
//
//    @Override
//    public int getCount() {
//        return fragments.size();
//    }
//
//    public void updateData(List<BaseFragment> dataList) {
//        ArrayList<BaseFragment> fragments = new ArrayList<>();
//        for (int i = 0, size = dataList.size(); i < size; i++) {
//            Log.e("FPagerAdapter1", dataList.get(i).toString());
//            fragments.add(dataList.get(i));
//        }
//        setFragments(fragments);
//    }
//
//    private void setFragments(ArrayList<BaseFragment> mFragmentList) {
//        if(this.fragments != null){
//            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//            for(BaseFragment f:this.fragments){
//                fragmentTransaction.remove(f);
//            }
//            fragmentTransaction.commit();
//            mFragmentManager.executePendingTransactions();
//        }
//        this.fragments = mFragmentList;
//        notifyDataSetChanged();
//    }
//
//
//
//
//}