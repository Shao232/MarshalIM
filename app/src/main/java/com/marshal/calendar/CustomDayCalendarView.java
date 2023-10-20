package com.marshal.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import com.marshal.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomDayCalendarView extends View {

    private final Context mContext;
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
    /**
     * 拖拽圆点的画笔
     */
    private Paint mMoreCirclePaint;

    //画框的高度
    private int heightSpaceSize;
    //统计绘制框的总高度
    private int drawCountHeight;

    //设置圆的点击范围 顶部圆的范围对象
    private RectF clickCircleUpRect;
    ////设置圆的点击范围 底部圆的范围对象
    private RectF clickCircleDownRect;
    //是否创建日程
    private boolean isCreatingSchedule = false;
    private final String[] timeArray = {
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23",
    };

    /**
     * 全部范围
     */
    private final ArrayList<RectF> drawRectList = new ArrayList<>();
    /**
     * 点击后的时间点 距离 start 00,end 01
     */
    private final HashMap<Integer, String> timeAreaMap = new HashMap<>();

    /**
     * key 是时间线上的01到24
     * value 是 举例:01时间对应的矩形rectF
     */
    private final HashMap<String, RectF> scheduleData = new HashMap<>();

    /**
     * 点击的范围
     */
    private RectF clickRt;
    private int mWidth, mHeight;

    //是否添加时间对应坐标轴数据成功,完成一次添加后就过滤
    private boolean addScheduleDataSuccess = false;

    private boolean moveClickAreaCircleUp = false;

    private final boolean moveClickAreaClickDown = false;


    private SelectAddScheduleClick selectAddScheduleClick;
    private String title = "添加日程";


    public boolean setInterceptClickMoveEvent() {
        return moveClickAreaCircleUp || moveClickAreaClickDown;
    }

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

    public void setSelectAddScheduleClick(SelectAddScheduleClick onSelectAddScheduleClick) {
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
        mAddSchedulePaint.setTextSize(dip2px(mContext, 12f));

        mMoreCirclePaint = new Paint();
        mMoreCirclePaint.setAntiAlias(false);
        mMoreCirclePaint.setColor(getResources().getColor(R.color.im_chat_sender_bubble_color));
        mMoreCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mMoreCirclePaint.setStrokeWidth(10f);

        heightSpaceSize = dip2px(mContext, 36f); // 每个单元格的大小，可以根据需要调整

    }

    private int downClickY = 0;
    private int moveClickY = 0;
    private final int minDistance = 5;
    private int distance = 0;
    private final int offsetY = 30;

    private CalendarHandler handler = new CalendarHandler();


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //传入true拦截父视图的触摸事件，让子视图（这个自定义自身）进行触摸滑动
        int clickY = (int) Math.abs(event.getY());
        int clickX = (int) Math.abs(event.getX());
        Log.d("TAG", "click x,Y :" + clickX + " , " + clickY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当选择了添加日程后
                if (isCreatingSchedule) {
                    downClickY = clickY;

                }
                break;
            case MotionEvent.ACTION_MOVE:
                //当选择了添加日程后
                moveClickAreaCircleUp = true;
                if (isCreatingSchedule) {
                    moveClickY = clickY;
                    Log.d("TAG", "downClickY :" + downClickY);
                    Log.d("TAG", "moveClickY :" + moveClickY);
                    Log.d("TAG", "up :" + clickCircleUpRect.toString());
                    if (clickY >= clickCircleUpRect.top && clickY <= clickCircleUpRect.bottom) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        distance = moveClickY - downClickY;
                        if (Math.abs(distance) > minDistance) {
                            handler.sendEmptyMessageDelayed(200, 400);
                            Log.d("TAG", "distance :" + distance);
                        }

                        invalidate();
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }


                break;
            case MotionEvent.ACTION_UP:


                // 检查触摸事件的坐标是否在屏幕范围内
                if (clickY < 0 || clickY > getHeight()) {
                    // 如果超出屏幕范围，可以选择忽略这个事件或者进行相应的处理
                    return true;
                }
                //如果手指点击的范围刚好是选择日期的范围，激活选择事件传递
                if (clickRt != null && (clickY >= clickRt.top && clickY <= clickRt.bottom)) {
                    String tempStr = timeAreaMap.get((int) clickRt.bottom);
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
                    if (selectAddScheduleClick != null) {
                        selectAddScheduleClick.onAddScheduleClickListener(startHour, selectHour);
                    }
                    return true;
                }

                //遍历全部区间，找到点击的区间范围
                for (RectF itemF : drawRectList) {
                    if (clickY >= itemF.top && clickY <= itemF.bottom) {
                        //点击了范围内的某个区间
                        clickRt = itemF;
                        isCreatingSchedule = true;
                        invalidate();
                        break;
                    }
                }

                moveClickAreaCircleUp = false;

                break;
            case MotionEvent.ACTION_CANCEL:
                moveClickAreaCircleUp = false;
                break;
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
        //创建RectF数据
        createRectFArray();
        //绘制每行铺满宽度，一共24列
        drawGrid(canvas);
        //绘制左侧的0到23个文字
        drawTimeText(canvas);

        //如果添加日程
        if (isCreatingSchedule) {
            //绘制添加日程的区域和文字
            drawCreateScheduleRect(canvas);
            //设置添加日程区域顶部的top圆点
            drawMoreScheduleUpCircle(canvas);
            drawMoreScheduleDownCircle(canvas);
        }
    }


    private void createRectFArray() {
        int spaceStartX = dip2px(mContext, 40f);
        drawCountHeight = 0;
        Log.d("TAG", "heightSize = " + heightSpaceSize);
        RectF rect1;
        for (int j = 0; j < 24; j++) {//绘制24次
            rect1 = new RectF(spaceStartX, drawCountHeight, mWidth, drawCountHeight + heightSpaceSize);
            drawRectList.add(rect1);
            drawCountHeight += heightSpaceSize;
        }
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
            canvas.drawText(i == 0 ? "" : timeArray[i], spaceStartX, drawTextY, mTextPaint);
            timeAreaMap.put(drawTextY, timeArray[i]);
            drawTextY += heightSpaceSize;
        }

        if (!addScheduleDataSuccess) {
            for (int i = 0; i < 24; i++) {
                scheduleData.put(timeArray[i], drawRectList.get(i));
            }
            addScheduleDataSuccess = true;
        }

    }

    /**
     * 绘制第一个添加日程的点击区域
     *
     * @param canvas
     */
    private void drawCreateScheduleRect(Canvas canvas) {
        canvas.drawRect(clickRt, mSchedulePaint);
        float drawY = clickRt.top + dip2px(mContext, 22f);
        float drawX = clickRt.right / 2;
        canvas.drawText(title, drawX, drawY, mAddSchedulePaint);
        handler.setMoveUpOffset(clickRt.top);
    }

    /**
     * 绘制向上扩展日程拖拽圆点
     *
     * @param canvas
     */
    private void drawMoreScheduleUpCircle(Canvas canvas) {
        float clickTop = clickRt.top;
        float clickRight = clickRt.right;
        float drawRadius = 15f;
        float drawArea = drawRadius + 5f;
        //如果top是0,就是顶部第一个，不在上添加圆点

        float drawCircleX = clickRight / 2 + dip2px(mContext, 24f);
        float drawCircleY = Math.abs(clickTop);
        clickCircleUpRect = new RectF(clickRt.left, clickTop - drawArea, clickRt.right, clickTop + heightSpaceSize - drawArea);

        canvas.drawCircle(drawCircleX, drawCircleY, drawRadius, mMoreCirclePaint);
    }

    /**
     * 绘制点击区域的底部拖拽
     *
     * @param canvas
     */
    private void drawMoreScheduleDownCircle(Canvas canvas) {
        float clickRight = clickRt.right;
        float clickBottom = clickRt.bottom;
        float drawRadius = 15f;
        float drawArea = drawRadius + 5f;
        //如果bottom是最后一个的底部，就不显示

        float drawCircleX = clickRight / 2 + dip2px(mContext, 24f);
        float drawCircleY = Math.abs(clickBottom);
        clickCircleDownRect = new RectF(clickRt.left, clickBottom - heightSpaceSize + drawArea, clickRt.right, clickBottom + drawArea);

        canvas.drawCircle(drawCircleX, drawCircleY, drawRadius, mMoreCirclePaint);
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    public interface SelectAddScheduleClick {
        void onAddScheduleClickListener(int startTime, int endTime);
    }

    protected class CalendarHandler extends Handler {

        public CalendarHandler() {
            super(Looper.getMainLooper());
        }

        private float moveUpOffset;

        public void setMoveUpOffset(float moveUpOffset) {
            this.moveUpOffset = moveUpOffset;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                Log.d("TAG", "handler massage ");
                Log.d("TAG", "handler moveClickAreaCircleUp :"+moveClickAreaCircleUp);
                if (moveClickAreaCircleUp) {
                    moveUpOffset -= offsetY;
                    Log.d("TAG", "moveClickAreaCircleUp ：" + moveUpOffset);
                    if (clickRt.top - moveUpOffset > 0) {
                        clickRt.top = moveUpOffset;
                    }
                }

            }
        }
    }

}
