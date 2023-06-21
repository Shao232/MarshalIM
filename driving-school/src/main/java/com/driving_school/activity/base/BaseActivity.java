//package com.driving_school.activity.base;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Message;
//import android.text.SpannableString;
//import android.text.TextUtils;
//import android.text.style.ForegroundColorSpan;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.widget.Toolbar;
//import androidx.viewbinding.ViewBinding;
//
//import com.driving_school.R;
//import com.driving_school.utils.ToastUtil;
//import com.marshal.base_common.baseview.BaseViewActivity;
//
//public abstract class BaseActivity<T extends ViewBinding> extends BaseViewActivity<T> {
//
//    public T mBinding = getBinding();
//    protected BaseHandler handler;
//    protected Toolbar toolbar;
//    protected TextView tvTitle;
//    private View.OnClickListener onClickListenerTopLeft;
//    private View.OnClickListener onClickListenerTopRight;
//    protected int menuResId;
//    protected String menuStr;
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {
//            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
//            MenuItem item = menu.findItem(R.id.menu_1).setTitle(menuStr);
//            SpannableString spannableString = new SpannableString((item.getTitle()));
//            spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), 0);
//            item.setTitle(spannableString);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (menuResId != 0) {
//            menu.findItem(R.id.menu_1).setIcon(menuResId);
//        }
//
//        if (!TextUtils.isEmpty(menuStr)) {
//            menu.findItem(R.id.menu_1).setTitle(menuStr);
//        }
//
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            if (onClickListenerTopLeft != null) {
//                onClickListenerTopLeft.onClick(toolbar);
//            }
//        } else if (item.getItemId() == R.id.menu_1) {
//            if (onClickListenerTopRight != null) {
//                onClickListenerTopRight.onClick(toolbar);
//            }
//        }
//        return true;
//    }
//
//    protected void startPhone(String uri) {
//        if (uri == null || "".equals(uri))
//            return;
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + uri));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//    protected void HandlerMessage(Context mContext, Message msg) {
//    }
//
//    public void showToast(String msg) {
//        ToastUtil.showToast(getApplicationContext(), msg);
//    }
//
//    protected void startActivity(Class<?> clazz) {
//        Intent intent = new Intent(this, clazz);
//        startActivity(intent);
//    }
//
//    protected void startActivity(Bundle bundle, Class<?> clazz) {
//        Intent intent = new Intent(this, clazz);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//    private static class MyHandler extends BaseHandler {
//
//        MyHandler(Context mContext) {
//            super(mContext);
//        }
//
//        @Override
//        protected void handleMessage(Context mContext, Message msg) {
//            BaseActivity activity = (BaseActivity) mContext;
//            if (activity != null) {
//                activity.HandlerMessage(mContext, msg);
//            }
//        }
//    }
//
//}
