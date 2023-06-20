//package com.driving_school.activity;
//
//import android.view.View;
//
//import androidx.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.driving_school.R;
//import com.driving_school.activity.base.BaseActivity;
//import com.driving_school.databinding.ActivityExplainBinding;
//import com.driving_school.utils.SharedPreferenceUtils;
//
//public class ExplainActivity extends BaseActivity<ActivityExplainBinding> {
//
//    @Nullable
//    @Override
//    public View getResLayoutBinding() {
//        mBinding = ActivityExplainBinding.inflate(getLayoutInflater());
//        return mBinding.getRoot();
//    }
//
//    @Override
//    public void initView() {
//        String userHead = SharedPreferenceUtils.getParams("userHead",this);
//        String userName = SharedPreferenceUtils.getParams("userName",this);
//        Glide.with(getApplicationContext()).load(userHead)
//                .placeholder(R.drawable.defult_img)
//                .error(R.drawable.defult_img)
//                .into(mBinding.ivUserHeadExplain);
//        mBinding.tvUserNicknameExplain.setText(userName);
//    }
//
//
//}
