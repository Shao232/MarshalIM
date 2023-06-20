//package com.driving_school.activity;
//
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.driving_school.R;
//import com.driving_school.activity.base.BaseActivity;
//import com.driving_school.databinding.ActivityShowBigImageBinding;
//import com.driving_school.utils.StringUtil;
//
//public class ShowBigImageActivity extends BaseActivity<ActivityShowBigImageBinding> {
//
//
//    @Nullable
//    @Override
//    public View getResLayoutBinding() {
//        mBinding = ActivityShowBigImageBinding.inflate(getLayoutInflater());
//        return mBinding.getRoot();
//    }
//
//    @Override
//    public void initView() {
//        String url = getIntent().getExtras().getString("url");
//        if (StringUtil.isNotEmpty(url)){
//            Glide.with(this).load(url).error(R.drawable.defult_img).into(mBinding.ivShowImage);
//        }
//    }
//}
