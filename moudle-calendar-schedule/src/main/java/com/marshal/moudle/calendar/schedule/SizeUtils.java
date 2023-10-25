package com.marshal.moudle.calendar.schedule;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class SizeUtils {

    /**
     * 获取高度
     * @param context
     * @return
     */
    public static int  getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getDisplayMetrics(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        // 将密度转换为 dpi (每英寸点数)
        return (int) (density * 160);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (pxValue / scale + 0.5f));
    }


}