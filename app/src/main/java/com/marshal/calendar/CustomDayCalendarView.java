package com.marshal.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

public class CustomDayCalendarView extends View implements View.OnClickListener {

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

    //画框的高度
    private int heightSpaceSize;
    //统计绘制框的总高度
    private int drawCountHeight;

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

    private static final int TOP = 0x21;
    private static final int BOTTOM = 0x23;
    private static final int CENTER = 0x25;
    private final int offset = 50;

    private int dragDirection;
    private int lastX;
    private int lastY;

    // view的宽、高初始化
    private int rectTop = 0;
    private int rectLeft = 0;
    private int rectRight = 0;
    private int rectBottom = 0;
    // 线条的宽度
    private final int mLineSize = 3;
    // 图片大小
    private final int mRectSize = 50;
    //添加日程蓝色点击区域
    private Paint mAddScheduleBlueAreaPaint;
    private Rect mRect;
    private Paint.Style mStyle;
    // 拉伸的view
    private Bitmap mBottomBmp;
    private Rect mBottomRect;
    private Bitmap mTopBmp;
    private Rect mTopRect;

    private boolean moveBlueArea = false;

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

    public boolean setIntercept(){
        return isCreatingSchedule && moveBlueArea;
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
        mSchedulePaint.setColor(Color.TRANSPARENT);
        mSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mAddSchedulePaint = new Paint();
        mAddSchedulePaint.setAntiAlias(false);
        mAddSchedulePaint.setColor(Color.WHITE);
        mAddSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAddSchedulePaint.setStrokeWidth(1f);
        mAddSchedulePaint.setTextSize(dip2px(mContext, 12f));

        mAddScheduleBlueAreaPaint = new Paint();
        mStyle = Paint.Style.FILL_AND_STROKE;
        mAddScheduleBlueAreaPaint.setColor(getResources().getColor(R.color.select_schedule_bg));
        mAddScheduleBlueAreaPaint.setAntiAlias(true);
        mAddScheduleBlueAreaPaint.setStyle(mStyle);
        mAddScheduleBlueAreaPaint.setStrokeWidth((float) mLineSize);

        mTopBmp = BitmapFactory.decodeResource(getResources(), R.drawable.stretch_bottom);
        mBottomBmp = BitmapFactory.decodeResource(getResources(), R.drawable.stretch_bottom);
        mBottomRect = new Rect();
        mTopRect = new Rect();
        mRect = new Rect();


        setOnClickListener(this);
        setSelected(true);
        heightSpaceSize = dip2px(mContext, 36f); // 每个单元格的大小，可以根据需要调整

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //传入true拦截父视图的触摸事件，让子视图（这个自定义自身）进行触摸滑动
        int clickY = (int) Math.abs(event.getY());

        int action = event.getAction();
        handleDrag(event, action);
        invalidate();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                dragDirection = 0;
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
                        break;
                    }
                }
                if (isCreatingSchedule) {
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                dragDirection = 0;
                break;
        }

        return true;
    }

    private void handleDrag(MotionEvent event, int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            setSelected(true);
            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();
            dragDirection = getDirection((int) event.getX(), (int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
            moveBlueArea = isCreatingSchedule;

            int dx = ((int) event.getRawX()) - lastX;
            int dy = ((int) event.getRawY()) - lastY;
            switch (dragDirection) {
                case BOTTOM:
                    bottom(dy);
                    break;
                case CENTER:
                    break;
                case TOP:
                    top(dy);
                    break;
            }
            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();
        } else {
            moveBlueArea = false;
        }

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
            canvas.save();

            Log.d("TAG", "drag onDraw left :" + clickRt.left);
            Log.d("TAG", "drag onDraw  top :" + clickRt.top);
            Log.d("TAG", "drag onDraw  right :" + clickRt.right);
            Log.d("TAG", "drag onDraw  bottom :" + clickRt.bottom);
            rectLeft = (int) clickRt.left;
            rectTop = (int) clickRt.top;
            rectRight = (int) clickRt.right;
            rectBottom = (int) clickRt.bottom;

            mRect.set(rectLeft, rectTop, rectRight, rectBottom);
            canvas.drawRect(mRect, mAddScheduleBlueAreaPaint);

            canvas.restore();
            if (isSelected()) {
                //取right - left 就是整个长度，然后取值长度的一半，从left起点加上一半
                int left = mRect.left + ((mRect.right - mRect.left) / 2);

                mTopRect.set(left - (mRectSize / 2), mRect.top - (mRectSize / 2), left + (mRectSize / 2), mRect.top + (mRectSize / 2));
                canvas.drawBitmap(mTopBmp, null, mTopRect, this.mAddScheduleBlueAreaPaint);

                mBottomRect.set(left - (mRectSize / 2), mRect.bottom - (mRectSize / 2), left + (mRectSize / 2), mRect.bottom + (mRectSize / 2));
                canvas.drawBitmap(mBottomBmp, null, mBottomRect, this.mAddScheduleBlueAreaPaint);
            }

            //绘制添加日程的区域和文字
//            drawCreateScheduleRect(canvas);
            //设置添加日程区域顶部的top圆点
//            drawMoreScheduleUpCircle(canvas);
//            drawMoreScheduleDownCircle(canvas);
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
     * 通过坐标和移动距离，确定移动方向
     *
     * @param x
     * @param y
     * @return
     */
    private int getDirection(int x, int y) {
        int topTop = mTopRect.top;
        int topBottom = mTopRect.bottom;

        int upTop = mBottomRect.top;
        int upBottom = mBottomRect.bottom;

        if (y > (topTop - offset) && y < (topBottom + offset)) {
            return TOP;
        }

        if (y > (upTop - offset) && y < (upBottom + offset)) {
            return BOTTOM;
        }
        return CENTER;
    }


    /**
     * 设置滑动顶部位置，位置的数值
     *
     * @param dy
     */
    private void top(int dy) {
        Log.d("TAG", "...top... dy :" + dy);
        Log.d("TAG", "...v.top...  :" + getTop());
        rectTop += dy;
        if (rectTop < getTop()) {
            rectTop = getTop();
        } else if (rectTop > (rectBottom - offset)) {
            rectTop = rectBottom - offset;
        }
    }

    /**
     * 设置滑动底部位置，位置的数值
     *
     * @param dy
     */
    private void bottom(int dy) {
        Log.d("TAG", "...bottom... dy :" + dy);
        rectBottom += dy;
        if (rectBottom < (rectTop + offset)) {
            rectBottom = rectTop + offset;
        } else if (rectBottom > getHeight() - offset) {
            rectBottom = getHeight() - offset;
        }
    }


    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    @Override
    public void onClick(View v) {
    }


    public interface SelectAddScheduleClick {
        //第二次点击添加日程时跳转界面
        void onAddScheduleClickListener(int startTime, int endTime);
    }


}
