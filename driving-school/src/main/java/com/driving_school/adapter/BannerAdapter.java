//package com.driving_school.adapter;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.driving_school.R;
//import com.driving_school.convenientbanner.holder.Holder;
//
///**
// * Created by 11470 on 2017/10/18.
// */
//
//public class BannerAdapter implements Holder<String> {
//
//    private ImageView imageView;
//
//    @Override
//    public View createView(Context context) {
//        imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        return imageView;
//    }
//
//    @Override
//    public void UpdateUI(Context context, int position, String data) {
//        Glide.with(context).load(data).placeholder(R.drawable.defult_img)
//                .fallback(R.drawable.defult_img).error(R.drawable.defult_img)
//                .into(imageView);
//    }
//}
