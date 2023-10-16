package com.marshal.calendar;

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

public class CustomDayCalendarView extends View {

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
    //是否创建日程
    private boolean isCreatingSchedule = false;
    private String[] timeArray = {
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23",
    };

    /**
     * 全部范围
     */
    private ArrayList<RectF> drawRectList = new ArrayList<>();
    /**
     * 点击后的时间点 距离 start 00,end 01
     */
    private HashMap<Integer,String> timeAreaMap = new HashMap<>();
    /**
     * 点击的范围
     */
    private RectF clickRt;
    private int mWidth,mHeight;

    private SelectAddScheduleClick selectAddScheduleClick;
    private String title = "添加日程";

    public CustomDayCalendarView(Context context) {
        this(context, null);
    }

    public CustomDayCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDayCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setSelectAddScheduleClick(SelectAddScheduleClick onSelectAddScheduleClick){
        this.selectAddScheduleClick = onSelectAddScheduleClick;
    }

    public void setScheduleContent(String title){
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
        mAddSchedulePaint.setTextSize(dip2px(mContext, 12f));

        heightSpaceSize =  dip2px(mContext, 36f); // 每个单元格的大小，可以根据需要调整
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float clickY = (int)Math.abs(event.getY());
        if(event.getAction() == MotionEvent.ACTION_UP) {
            // 检查触摸事件的坐标是否在屏幕范围内
            if (clickY < 0 || clickY > getHeight()) {
                // 如果超出屏幕范围，可以选择忽略这个事件或者进行相应的处理
                return true;
            }

            if(clickRt !=null && (clickY >= clickRt.top && clickY <= clickRt.bottom)) {
                Log.i("TAG","click bottom :"+ clickRt.bottom);
                Log.i("TAG","keySet :"+ timeAreaMap.keySet());
                String tempStr = timeAreaMap.get((int)clickRt.bottom);
                Log.i("TAG","Text :"+tempStr);
                String selectTime = "";
                if(!TextUtils.isEmpty(tempStr)) {
                    selectTime =tempStr;
                }else {
                    selectTime = "24";
                }
                int selectHour = 0;
                if(!TextUtils.isEmpty(selectTime)) {
                    selectHour = Integer.parseInt(selectTime);
                }

                int startHour = 0;
                if(selectHour > 0) {
                    startHour = selectHour -1;
                }
                selectHour = selectHour == 24? 0:selectHour;
                Log.d("TAG","startHour :" + startHour +", endHour : "+ selectHour);
                if(selectAddScheduleClick != null) {
                    selectAddScheduleClick.onAddScheduleClickListener(startHour, selectHour);
                }
                return true;
            }

             for (RectF itemF:drawRectList) {
                 if(clickY >= itemF.top && clickY <= itemF.bottom) {
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

    private void createRectFArray(){
        int spaceStartX = dip2px(mContext, 40f);
        int drawHeight = 0;
        Log.d("TAG", "heightSize = " + heightSpaceSize);
        RectF rect1;
        for (int j = 0; j < 24; j++) {//绘制24次
            rect1 = new RectF(spaceStartX,drawHeight,mWidth,drawHeight + heightSpaceSize);
            drawRectList.add(rect1);
            drawHeight += heightSpaceSize;
        }
        Log.d("TAG", "drawHeight = " + drawHeight);
    }

    private void drawGrid(Canvas canvas) {
        for (int j = 0; j < 24; j++) {//绘制24次
            canvas.drawRect(drawRectList.get(j), mPaint);
        }
    }

    private void drawTimeText(Canvas canvas) {
        int spaceStartX = dip2px(mContext, 10f);
        int drawTextY = 0;
        for (int i = 0; i < 24; i++) {
            canvas.drawText(i == 0?"":timeArray[i],spaceStartX,drawTextY,mTextPaint);
            timeAreaMap.put(drawTextY,timeArray[i]);
            drawTextY +=heightSpaceSize;
        }
    }



    private void drawCreateScheduleRect(Canvas canvas) {
        canvas.drawRect(clickRt,mSchedulePaint);
        float drawY = clickRt.top + dip2px(mContext,18f);
        float drawX = clickRt.right /2;
        canvas.drawText(title,drawX,drawY,mAddSchedulePaint);
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    public interface SelectAddScheduleClick{
        void onAddScheduleClickListener(int startTime,int endTime);
    }

}
