package com.marshal.moudle.calendar.schedule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomWeekCalendarView extends View {

    private Context mContext;
    /**
     * 线条画笔
     */
    private Paint mPaint;
    /**
     * 时间点画笔
     */
    private Paint mTextPaint;
    /**
     * 点击日程文字画笔
     */
    private Paint mAddSchedulePaint;
    /**
     * 创建日程的画笔
     */
    private Paint mSchedulePaint;
    //画框的高度
    private int heightSpaceSize;
    //画框的宽度
    private int widthSpaceSize;
    //是否创建日程
    private boolean isCreatingSchedule = false;
    private String[] timeArray = {
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23",
    };

    /**
     * 全部范围
     */
    private ArrayList<WeekRectBean> drawRectList = new ArrayList<>();
    /**
     * 点击后的时间点 距离 start 00,end 01
     */
    private HashMap<Integer, String> timeAreaMap = new HashMap<>();

    /**
     * 周视图下给每个格子矩形。
     *
     */
    private HashMap<Integer,  ArrayList<WeekRectBean>> weekAreaMap = new HashMap<>();
    /**
     * 点击的范围
     */
    private WeekRectBean clickRt;
    private int mWidth, mHeight;

    private SelectWeekAddScheduleClick selectAddScheduleClick;
    private String title = "添加日程";

    public CustomWeekCalendarView(Context context) {
        this(context, null);
    }

    public CustomWeekCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWeekCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setSelectAddScheduleClick(SelectWeekAddScheduleClick onSelectAddScheduleClick) {
        this.selectAddScheduleClick = onSelectAddScheduleClick;
    }

    public void setScheduleContent(String title) {
        this.title = title;
        invalidate();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(false);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setStrokeWidth(1f);
        mTextPaint.setTextSize(dip2px(mContext, 12f));

        mSchedulePaint = new Paint();
        mSchedulePaint.setAntiAlias(false);
        mSchedulePaint.setColor(Color.BLUE);
        mSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mAddSchedulePaint = new Paint();
        mAddSchedulePaint.setAntiAlias(false);
        mAddSchedulePaint.setColor(Color.WHITE);
        mAddSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAddSchedulePaint.setStrokeWidth(1f);
        mAddSchedulePaint.setTextSize(dip2px(mContext, 8f));


        heightSpaceSize = dip2px(mContext, 36f); // 每个单元格的大小，可以根据需要调整
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float clickY = (int) Math.abs(event.getY());
        float clickX = (int) Math.abs(event.getX());
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 检查触摸事件的坐标是否在屏幕范围内
            if (clickY < 0 || clickY > getHeight()) {
                // 如果超出屏幕范围，可以选择忽略这个事件或者进行相应的处理
                return true;
            }

            if (clickRt != null && (clickY >= clickRt.rectF.top
                    && clickY <= clickRt.rectF.bottom
                    && clickX >= clickRt.rectF.left && clickX <= clickRt.rectF.right)) {
                Log.i("TAG", "click bottom :" + clickRt.rectF.bottom);
                Log.i("TAG", "keySet :" + timeAreaMap.keySet());
                String tempStr = timeAreaMap.get((int) clickRt.rectF.bottom);
                Log.i("TAG", "Text :" + tempStr);


                String selectTime = "";
                if (!TextUtils.isEmpty(tempStr)) {
                    selectTime = tempStr;
                } else {
                    selectTime = "24";
                }
                int selectHour = 0;
                if (!TextUtils.isEmpty(selectTime)) {
                    selectHour = Integer.parseInt(selectTime);
                }

                int startHour = 0;
                if (selectHour > 0) {
                    startHour = selectHour - 1;
                }
                selectHour = selectHour == 24 ? 0 : selectHour;
                Log.d("TAG", "startHour :" + startHour + ", endHour : " + selectHour
                +", weekInfo :" + clickRt.weekInfo);

                if (selectAddScheduleClick != null) {
                    selectAddScheduleClick.onAddWeekScheduleClickListener(startHour, selectHour, clickRt.weekInfo);
                }

                return true;
            }

            for (WeekRectBean itemF : drawRectList) {
                if (clickY >= itemF.rectF.top && clickY <= itemF.rectF.bottom
                        && clickX >= itemF.rectF.left && clickX <= itemF.rectF.right) {
                    //点击了范围内的某个区间
                    clickRt = itemF;
                    isCreatingSchedule = true;
                    invalidate();
                    break;
                }
            }
        }

        return true;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 根据需要设置自定义View的高度
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建recf数据
        createRectFArray();
        drawGrid(canvas);
        drawTimeText(canvas);

        if (isCreatingSchedule) {
            drawCreateScheduleRect(canvas);
        }
    }

    private void createRectFArray() {
        widthSpaceSize = (mWidth - dip2px(mContext, 40f)) / 7;
        int spaceStartX = dip2px(mContext, 40f);
        int drawHeight = 0;
        RectF rect1;
        for (int j = 0; j < 24; j++) {//这是列
            for (int i = 0; i < 7; i++) {//这是行
                rect1 = new RectF(spaceStartX, drawHeight, spaceStartX + widthSpaceSize, drawHeight + heightSpaceSize);
                drawRectList.add(new WeekRectBean(i, rect1));
                spaceStartX += widthSpaceSize;
            }
            weekAreaMap.put(j,drawRectList);
            drawHeight += heightSpaceSize;
            spaceStartX = dip2px(mContext, 40f);
        }
    }

    private void drawGrid(Canvas canvas) {
        for (WeekRectBean item : drawRectList) {
            canvas.drawRect(item.rectF, mPaint);
        }
    }

    private void drawTimeText(Canvas canvas) {
        int spaceStartX = dip2px(mContext, 10f);
        int drawTextY = 0;
        for (int i = 0; i < 24; i++) {
            canvas.drawText(i == 0 ? "" : timeArray[i], spaceStartX, drawTextY, mTextPaint);
            timeAreaMap.put(drawTextY, timeArray[i]);
            drawTextY += heightSpaceSize;
        }
    }


    private void drawCreateScheduleRect(Canvas canvas) {

        canvas.drawRect(clickRt.rectF, mSchedulePaint);
        float drawY = clickRt.rectF.top + dip2px(mContext, 16f);
        float drawX = clickRt.rectF.left + dip2px(mContext, 5f);
        canvas.drawText(title, drawX, drawY, mAddSchedulePaint);
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    public interface SelectWeekAddScheduleClick {
        void onAddWeekScheduleClickListener(int startTime, int endTime,int weekInfo);
    }

    private static class WeekRectBean{
        private final int weekInfo;//0,1,2..固定0到6，在外部进行逻辑判断
        private final RectF rectF;//矩形

        public WeekRectBean(int weekInfo,RectF rectF){
            this.weekInfo = weekInfo;
            this.rectF = rectF;
        }

    }


}
