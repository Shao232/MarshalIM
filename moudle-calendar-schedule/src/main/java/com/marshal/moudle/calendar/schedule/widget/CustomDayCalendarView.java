package com.marshal.moudle.calendar.schedule.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;


import com.marshal.moudle.calendar.schedule.R;
import com.marshal.moudle.calendar.schedule.utils.SizeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomDayCalendarView extends View implements View.OnClickListener, View.OnTouchListener {

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
    private static final int OUTER = 0x26;
    private final int offset = 30;

    private int dragDirection;
    private int lastX;
    private int lastY;
    //记录一个可以添加日程的y坐标
    private int clickScheduleY;

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
    /**
     * 蓝色触摸区域
     */
    private Rect mBlueAreaRt;
    private Paint.Style mStyle;
    // 拉伸的view
    private Bitmap mBottomBmp;
    private Rect mBottomRect;
    private Bitmap mTopBmp;
    private Rect mTopRect;


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
        mAddSchedulePaint.setTextSize(dip2px(mContext, 14f));

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
        mBlueAreaRt = new Rect();

        setOnTouchListener(this);
        setOnClickListener(this);
        setSelected(true);
        heightSpaceSize = dip2px(mContext, 50f); // 每个单元格的大小，可以根据需要调整

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeight = SizeUtils.dip2px(getContext(),50f * 32);
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }


    @Override
    public void onClick(View v) {
        //如果手指点击的范围刚好是选择日期的范围，激活选择事件传递
        Log.d("TAG", "click clickScheduleY :" + clickScheduleY);

        if (mBlueAreaRt != null && (clickScheduleY >= mBlueAreaRt.top && clickScheduleY <= mBlueAreaRt.bottom)) {
            Log.d("TAG", "click top :" + mBlueAreaRt.top + ",bottom :" + mBlueAreaRt.bottom);

            int blueAreaMin = Math.min(mBlueAreaRt.top, mBlueAreaRt.bottom);
            int blueAreaMax = Math.max(mBlueAreaRt.top, mBlueAreaRt.bottom);
            Log.d("TAG", "min :" + blueAreaMin);
            Log.d("TAG", "max :" + blueAreaMax);

            String startTime = "";
            String selectTime = "";
            for (Map.Entry<String, RectF> scheduleRect : scheduleData.entrySet()) {
                RectF rectValue = scheduleRect.getValue();
                int minData = Math.min((int) rectValue.top, (int) rectValue.bottom);
                int maxData = Math.max((int) rectValue.top, (int) rectValue.bottom);
                if (blueAreaMin == minData) {
                    startTime = scheduleRect.getKey();
                }
                if (blueAreaMax == maxData) {
                    selectTime = scheduleRect.getKey();
                }
            }
            Log.d("TAG", "start :" + startTime + " , endTime :" + selectTime);

            int startHour;
            int selectHour;
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(selectTime)) {
                startHour = Integer.parseInt(startTime);
                selectHour = Integer.parseInt(selectTime);
            }else {
                Toast.makeText(mContext,"请选择完整的时间点",Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectAddScheduleClick != null) {
                Log.d("TAG","startHour :"+startHour);
                Log.d("TAG","selectHour :"+selectHour);
                selectAddScheduleClick.onAddScheduleClickListener(startHour, selectHour);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //传入true拦截父视图的触摸事件，让子视图（这个自定义自身）进行触摸滑动
        int clickY = (int) Math.abs(event.getY());
        if (isCreatingSchedule) {
            int action = event.getAction();
            handleDrag(event, action);
            invalidate();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                switch (dragDirection) {
                    case TOP, BOTTOM -> {
//                        Log.d("TAG", "... onTouch ACTION_MOVE event... top bottom ");
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    default -> {
//                        Log.d("TAG", "... onTouch ACTION_MOVE event... default ");
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
            case MotionEvent.ACTION_UP -> {
                // 检查触摸事件的坐标是否在屏幕范围内
                if (clickY < 0 || clickY > getHeight()) {
                    // 如果超出屏幕范围，可以选择忽略这个事件或者进行相应的处理
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
                if (isCreatingSchedule && clickRt != null) {

                    //第一次找到点击区域，进行重绘和赋值
                    if (rectLeft == 0 && rectTop == 0 && rectRight == 0 && rectBottom == 0) {
                        rectLeft = (int) clickRt.left;
                        rectTop = (int) clickRt.top;
                        rectRight = (int) clickRt.right;
                        rectBottom = (int) clickRt.bottom;
                        dragDirection = 0;
                        invalidate();
                        return true;
                    }

                    //当点击区域完成第一次重绘，进行判断是否是触摸滑动，
                    dragDirection = getDirection((int) v.getX(), clickY);

                    //如何滑动的是top，bottom进行重绘,是center事件不拦截，进行click事件
                    switch (dragDirection) {
                        case TOP -> {
//                            Log.d("TAG", "... onTouch up event... top bottom ");
                            if (clickRt.top < rectTop) {
                                rectTop = (int) clickRt.top;
                            }
                            dragDirection = 0;
                            invalidate();
                            return true;
                        }
                        case BOTTOM -> {
                            if (clickRt.bottom > rectBottom) {
                                rectBottom = (int) clickRt.bottom;
                            }
                            dragDirection = 0;
                            invalidate();
                            return true;
                        }
                        case CENTER -> {
                            //点击到蓝色区域内部
                            Log.d("TAG", "..... 点击到蓝色区域 ....");
                            clickScheduleY = clickY;
                            return false;
                        }
                        default -> {
//                            Log.d("TAG", "... onTouch up event... default ");
                            //如果点击到外部某个单元格，就替换绘制
                            rectLeft = (int) clickRt.left;
                            rectTop = (int) clickRt.top;
                            rectRight = (int) clickRt.right;
                            rectBottom = (int) clickRt.bottom;
                            dragDirection = 0;
                            invalidate();
                            return true;
                        }
                    }
                }
            }
            case MotionEvent.ACTION_CANCEL -> {
                dragDirection = 0;
            }
        }
        return false;
    }

    private void handleDrag(MotionEvent event, int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            setSelected(true);
            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();
            dragDirection = getDirection((int) event.getX(), (int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
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
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        int parentWidth = MeasureSpec.getSize(mWidth);
        int parentHeight = MeasureSpec.getSize(mHeight);
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
        drawScheduleAreaBlue(canvas);


    }


    private void createRectFArray() {
        int spaceStartX = dip2px(mContext, 40f);
        drawCountHeight = 0;
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

    private void drawScheduleAreaBlue(Canvas canvas) {
        if (isCreatingSchedule) {
            canvas.save();
            mBlueAreaRt.set(rectLeft, rectTop, rectRight, rectBottom);
            canvas.drawRect(mBlueAreaRt, mAddScheduleBlueAreaPaint);
            canvas.restore();
            if (isSelected()) {
                //取right - left 就是整个长度，然后取值长度的一半，从left起点加上一半
                int left = mBlueAreaRt.left + ((mBlueAreaRt.right - mBlueAreaRt.left) / 2);
                //绘制图标
                mTopRect.set(left - (mRectSize / 2), mBlueAreaRt.top - (mRectSize / 2), left + (mRectSize / 2), mBlueAreaRt.top + (mRectSize / 2));
                canvas.drawBitmap(mTopBmp, null, mTopRect, this.mAddScheduleBlueAreaPaint);
                //绘制图标
                mBottomRect.set(left - (mRectSize / 2), mBlueAreaRt.bottom - (mRectSize / 2), left + (mRectSize / 2), mBlueAreaRt.bottom + (mRectSize / 2));
                canvas.drawBitmap(mBottomBmp, null, mBottomRect, this.mAddScheduleBlueAreaPaint);
            }

            canvas.drawText(title, rectLeft + offset, rectTop + (offset * 2), mAddSchedulePaint);
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

        if (y > (topTop) && y < (topBottom + offset)) {
            return TOP;
        }

        if (y > (upTop - offset) && y < (upBottom)) {
            return BOTTOM;
        }

        //判断是否在外部
        if (y < (topTop - offset) || y > (upBottom + offset)) {
            return OUTER;
        }

        return CENTER;
    }


    /**
     * 设置滑动顶部位置，位置的数值
     *
     * @param dy
     */
    private void top(int dy) {
//        Log.d("TAG", "...top... dy :" + dy);
//        Log.d("TAG", "...v.top...  :" + getTop());
//        Log.d("TAG", "...v.top...  :" + getTop());
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
//        Log.d("TAG", "...bottom... dy :" + dy);
        RectF lastRect = drawRectList.get(drawRectList.size() - 1);
        rectBottom += dy;
        if (rectBottom < (rectTop + offset)) {
            rectBottom = rectTop + offset;
        } else if (rectBottom > (int) lastRect.bottom) {
            //如果大于最后一个单元格，就用最后一个的bottom设置到rectBottom
            rectBottom = (int) lastRect.bottom;
        }
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }


    public interface SelectAddScheduleClick {
        //第二次点击添加日程时跳转界面
        void onAddScheduleClickListener(int startTime, int endTime);
    }


}
