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
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleRectFBean;
import com.marshal.moudle.calendar.schedule.pojo.CalendarScheduleViewBean;
import com.marshal.moudle.calendar.schedule.utils.DayAndMonthUtils;
import com.marshal.moudle.calendar.schedule.utils.SizeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
    //画框的高度
    private int heightSpaceSize;

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
     * key 是时间线上的01到24
     * value 是 举例:01时间对应的矩形rectF
     */
    private final HashMap<String, RectF> scheduleData = new HashMap<>();

    private final ArrayList<CalendarScheduleViewBean> viewDataList = new ArrayList<>();
    private final ArrayList<CalendarScheduleRectFBean> scheduleRectFBeanList = new ArrayList<>();

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
    private int lastY;
    //记录一个可以添加日程的y坐标
    private int clickScheduleY;

    // view的宽、高初始化
    private int rectTop = 0;
    private int rectLeft = 0;
    private int rectRight = 0;
    private int rectBottom = 0;
    //添加日程蓝色点击区域
    private Paint mAddScheduleBlueAreaPaint;
    /**
     * 蓝色触摸区域
     */
    private Rect mBlueAreaRt;
    // 拉伸的view
    private Bitmap mBottomBmp;
    private Rect mBottomRect;
    private Bitmap mTopBmp;
    private Rect mTopRect;
    private SelectAddScheduleClick selectAddScheduleClick;
    private Paint addedScheduleAreaPaint;
    //绘制已经添加的日程边框
    private Paint addedScheduleFramePaint;
    private Paint addedScheduleFillPaint;

    //绘制已经添加的日程开始时间和结束时间的两个点，决定整个日程的范围
    private int drawTextX = 0;
    private int drawTextY = 0;
    private int drawTextAreaLeft = 0;
    private int drawTextAreaTop = 0;
    private int drawTextAreaRight = 0;
    private int drawTextAreaBottom = 0;


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

    public void setCalendarScheduleList(ArrayList<CalendarScheduleViewBean> arrayList) {
        if (arrayList != null) {
            this.viewDataList.clear();
            this.viewDataList.addAll(arrayList);
        } else {
            this.viewDataList.clear();
        }

        //处理数据，转换成rectF
        calendarScheduleDataParse();
        //刷新添加区域消失
        clearCreateScheduleArea();
        //重绘
        invalidate();
    }

    private void calendarScheduleDataParse() {
        //先清空
        scheduleRectFBeanList.clear();
        if (!viewDataList.isEmpty()) {

            //第一步转换数据
            dataParseRectFList();
            //排序数据
            Collections.sort((List) scheduleRectFBeanList);
            //反转，从大到小
            Collections.reverse(scheduleRectFBeanList);
            //布局
            for (int index = 0; index < scheduleRectFBeanList.size(); index++) {
                CalendarScheduleRectFBean itemBean = scheduleRectFBeanList.get(index);
                for(Map.Entry<String,RectF> entry : scheduleData.entrySet()){
                    if (itemBean.getRectDrawSchedule().top == entry.getValue().top) {
                        itemBean.setTopHour(entry.getKey());
                    }

                    if (itemBean.getRectDrawSchedule().bottom == entry.getValue().bottom) {
                        itemBean.setBottomHour(entry.getKey());
                    }
                }
            }

            //test 查看全部数据
            for (int index = 0; index < scheduleRectFBeanList.size(); index++) {
                CalendarScheduleRectFBean itemBean = scheduleRectFBeanList.get(index);
                Log.d("TAG","item :"+itemBean);
            }

            //单元格的宽度
            int tableWidth  = mWidth - dip2px(mContext, 40f);
            scheduleRectFBeanList.size();
            for (int index = 0; index < scheduleRectFBeanList.size(); index++) {
                CalendarScheduleRectFBean itemBean = scheduleRectFBeanList.get(index);

            }

              /*      变量
                  第一个 a   0 到 3  4格
                  第二个 b   1 到 2  2格
                  第三个 c   0 到 1  2格

                  遍历全部
                  a.top <= item.top && a.bottom >= item.bottom
                  a.right = 50
                  item.left = a.right + 10
                  item.right = 50

                */

        }
    }

    /**
     * 获取数据源
     * 得到日程的长度 比如 3格， 5格 等
     * 得到需要绘制日程数据
     * 全部转换成CalendarScheduleRectFBean对象后放入集合中
     */
    private void dataParseRectFList() {

        String startHourStr;
        String endHourStr;
        int index;
        for (index = 0; index < viewDataList.size(); index++) {
            CalendarScheduleViewBean itemBean = viewDataList.get(index);
            int startHour = itemBean.getStartHour();
            int endHour = itemBean.getEndHour();
            //计数从0开始,所以需要在最后加1
            //计算一个日程所占的单元格数量
            int tableCount = endHour - startHour + 1;
            if (startHour < 10) {
                startHourStr = "0" + startHour;
            } else {
                startHourStr = String.valueOf(startHour);
            }

            if (endHour < 10) {
                endHourStr = "0" + endHour;
            } else {
                endHourStr = String.valueOf(endHour);
            }

            RectF startRect = scheduleData.get(startHourStr);
            RectF endRect = scheduleData.get(endHourStr);
            RectF addScheduleTableAreaRect;

            if (startRect != null && endRect != null) {
                if (startHour == endHour) {
                    //如果开始时间等于结束时间,判断是一个单元格
                    addScheduleTableAreaRect = new RectF(startRect.left, startRect.top, startRect.right, startRect.bottom);
                } else {
                    addScheduleTableAreaRect = new RectF(startRect.left, startRect.top, startRect.right, endRect.bottom);
                }
                scheduleRectFBeanList.add(new CalendarScheduleRectFBean(index, tableCount, addScheduleTableAreaRect, itemBean.getTitle()));
            }
        }
    }


    /**
     * 清除添加区域的数据
     * 设置重新获取数据源时，添加区域消失
     */
    private void clearCreateScheduleArea() {
        isCreatingSchedule = false;
        rectLeft = 0;
        rectTop = 0;
        rectRight = 0;
        rectBottom = 0;
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

        mAddSchedulePaint = new Paint();
        mAddSchedulePaint.setAntiAlias(false);
        mAddSchedulePaint.setColor(Color.WHITE);
        mAddSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAddSchedulePaint.setStrokeWidth(1f);
        mAddSchedulePaint.setTextSize(dip2px(mContext, 14f));

        mAddScheduleBlueAreaPaint = new Paint();
//        mAddScheduleBlueAreaPaint.setColor(getResources().getColor(R.color.select_schedule_bg));
        mAddScheduleBlueAreaPaint.setColor(Color.argb(255, 0, 136, 244));
        mAddScheduleBlueAreaPaint.setAntiAlias(false);
        mAddScheduleBlueAreaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 线条的宽度
        mAddScheduleBlueAreaPaint.setStrokeWidth((float) 3f);

        addedScheduleAreaPaint = new Paint();
        addedScheduleAreaPaint.setAntiAlias(false);
        addedScheduleAreaPaint.setColor(getResources().getColor(R.color.show_added_schedule_bg));
        addedScheduleAreaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        addedScheduleAreaPaint.setStrokeWidth(1f);

        addedScheduleFramePaint = new Paint();
        addedScheduleFramePaint.setAntiAlias(false);
        addedScheduleFramePaint.setStyle(Paint.Style.STROKE);
        addedScheduleFramePaint.setStrokeWidth(5f);
        addedScheduleFramePaint.setColor(getResources().getColor(R.color.select_schedule_bg));

        addedScheduleFillPaint = new Paint();
        addedScheduleFillPaint.setAntiAlias(false);
        addedScheduleFillPaint.setStyle(Paint.Style.FILL);
        addedScheduleFillPaint.setStrokeWidth(5f);
        addedScheduleFillPaint.setColor(getResources().getColor(R.color.select_fill_schedule_bg));

        mTopBmp = BitmapFactory.decodeResource(getResources(), R.drawable.stretch_bottom);
        mBottomBmp = BitmapFactory.decodeResource(getResources(), R.drawable.stretch_bottom);
        mBottomRect = new Rect();
        mTopRect = new Rect();
        mBlueAreaRt = new Rect();

        setOnTouchListener(this);
        setOnClickListener(this);
        setSelected(true);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = SizeUtils.getScreenWidth(mContext);
                mHeight = SizeUtils.dip2px(getContext(), 50f * 32);
                // 每个单元格的大小，可以根据需要调整
                heightSpaceSize = dip2px(mContext, 50f);
                //创建RectF数据
                createRectFArray();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    @Override
    public void onClick(View v) {
        //如果手指点击的范围刚好是选择日期的范围，激活选择事件传递
        if (mBlueAreaRt != null && (clickScheduleY >= mBlueAreaRt.top && clickScheduleY <= mBlueAreaRt.bottom)) {
            int blueAreaMin = mBlueAreaRt.top;
            int blueAreaMax = mBlueAreaRt.bottom;

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

            int startHour;
            int selectHour;
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(selectTime)) {
                startHour = Integer.parseInt(startTime);
                selectHour = Integer.parseInt(selectTime);
            } else {
                Toast.makeText(mContext, "请选择完整的时间点", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectAddScheduleClick != null) {
                isCreatingSchedule = false;
                rectLeft = 0;
                rectTop = 0;
                rectRight = 0;
                rectBottom = 0;
                Log.d("TAG", "startHour :" + startHour + ", endHour :" + selectHour);
                selectAddScheduleClick.onAddScheduleClickListener(startHour, selectHour);
                postDelayed(this::invalidate, 300);

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
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    default -> {
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
                    dragDirection = getDirection(clickY);

                    //如何滑动的是top，bottom进行重绘,是center事件不拦截，进行click事件
                    switch (dragDirection) {
                        case TOP -> {
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
                            clickScheduleY = clickY;
                            return false;
                        }
                        default -> {
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
            lastY = (int) event.getRawY();
            dragDirection = getDirection((int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
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
            lastY = (int) event.getRawY();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(mWidth);
        int parentHeight = MeasureSpec.getSize(mHeight);
        // 根据需要设置自定义View的高度
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制每行铺满宽度，一共24列
        drawGrid(canvas);
        //绘制左侧的0到23个文字
        drawTimeText(canvas);

        //绘制已经有的日程
        drawAddedSchedule(canvas);

        //绘制添加日程
        drawScheduleAreaBlue(canvas);

    }

    /**
     * 创建日程24个单元格，将条目保存
     */
    private void createRectFArray() {
        int spaceStartX = dip2px(mContext, 40f);
        //统计绘制框的总高度
        int drawCountHeight = 0;
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
     * 绘制添加日程的区域
     *
     * @param canvas 画布
     */
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
                // 图片大小
                int mRectSize = 50;
                mTopRect.set(left - (mRectSize / 2), mBlueAreaRt.top - (mRectSize / 2), left + (mRectSize / 2), mBlueAreaRt.top + (mRectSize / 2));
                canvas.drawBitmap(mTopBmp, null, mTopRect, this.mAddScheduleBlueAreaPaint);
                //绘制图标
                mBottomRect.set(left - (mRectSize / 2), mBlueAreaRt.bottom - (mRectSize / 2), left + (mRectSize / 2), mBlueAreaRt.bottom + (mRectSize / 2));
                canvas.drawBitmap(mBottomBmp, null, mBottomRect, this.mAddScheduleBlueAreaPaint);
            }

            String title = "添加日程";
            canvas.drawText(title, rectLeft + offset, rectTop + (offset * 2), mAddSchedulePaint);
        }
    }


    /**
     * 绘制保存的日程
     * <p>
     * 1.循环保存的日程集合
     * 2.当时间段长的和时间段短的发生冲突，长的覆盖在上面
     *
     * @param canvas 画布
     */
    private void drawAddedSchedule(Canvas canvas) {
        if (!scheduleRectFBeanList.isEmpty()) {
//            int drawAddedTextOffset = dip2px(mContext, 6f);
//            int drawAddedAreaOffset = dip2px(mContext, 4f);
//            String startHourStr;
//            String endHourStr;
//            RectF tempOldScheduleBean = null;

            int index;
            for (index = 0; index < scheduleRectFBeanList.size(); index++) {
                CalendarScheduleRectFBean itemRect = scheduleRectFBeanList.get(index);

                canvas.drawRect(itemRect.getRectDrawSchedule(), addedScheduleFramePaint);
                canvas.drawRect(itemRect.getRectDrawSchedule(), addedScheduleFillPaint);
            }
        }


//        for (CalendarScheduleViewBean itemBean : viewDataList) {
//            int startHour = itemBean.getStartHour();
//            int endHour = itemBean.getEndHour();
//            //计数从0开始,所以需要在最后加1
//            //计算一个日程所占的单元格数量
//            int tableCount = endHour - startHour +1;
//
//            if (startHour < 10) {
//                startHourStr = "0" + startHour;
//            } else {
//                startHourStr = String.valueOf(startHour);
//            }
//
//            if (endHour < 10) {
//                endHourStr = "0" + endHour;
//            } else {
//                endHourStr = String.valueOf(endHour);
//            }
//
//            RectF startRect = scheduleData.get(startHourStr);
//            RectF endRect = scheduleData.get(endHourStr);
//
//            if (startRect != null && endRect != null) {
//                if (startHour == endHour) {
//                    //如果开始时间等于结束时间,判断是一个单元格
//                    addScheduleTableAreaRect = startRect;
//                } else {
//                    addScheduleTableAreaRect = new RectF(startRect.left, startRect.top, startRect.right, endRect.bottom);
//                }
//                Log.d("TAG", "addScheduleTableAreaRect :" + addScheduleTableAreaRect);
//
//
//                if (index == 0) {
//                    drawTextAreaLeft = (int) addScheduleTableAreaRect.left + drawAddedAreaOffset;
//                } else {
//                    if (tempOldScheduleBean != null && tempOldScheduleBean.top == addScheduleTableAreaRect.top
//                            && tempOldScheduleBean.bottom == addScheduleTableAreaRect.bottom) {
//                        //如果单元格和上一个单元格是相同的单元格内，
//                        drawTextAreaLeft = (int) drawTextAreaRight + drawAddedAreaOffset;
//                    } else {
//                        //如果单元格和上一个不一致了,需要重新初始化
//                        drawTextAreaLeft = (int) addScheduleTableAreaRect.left + drawAddedAreaOffset;
//
//                        int distance = (int)(addScheduleTableAreaRect.bottom-addScheduleTableAreaRect.top);
//                        Log.d("TAG","distance :"+distance);
//                        Log.d("TAG","table height :"+heightSpaceSize);
//
//
//                    }
//                }
//
//                drawTextAreaRight = Math.min((int) drawTextAreaLeft + (drawAddedAreaOffset * 10), mWidth);
//                drawTextAreaTop = (int) addScheduleTableAreaRect.top;
//                drawTextAreaBottom = (int) addScheduleTableAreaRect.bottom;
//
//                RectF drawTextAreaRect = new RectF(drawTextAreaLeft,drawTextAreaTop,drawTextAreaRight,drawTextAreaBottom);
//
//                drawTextX = (int) drawTextAreaRect.left + drawAddedTextOffset;
//                drawTextY = (int) drawTextAreaRect.top + dip2px(mContext, 20f);
//
//                canvas.drawRect(drawTextAreaRect, addedScheduleAreaPaint);
//
//                String showTitle;
//                if (itemBean.getTitle().length() >2) {
//                    showTitle = itemBean.getTitle().substring(0,2);
//                }else {
//                    showTitle = itemBean.getTitle();
//                }
//
//                canvas.drawText(showTitle, drawTextX, drawTextY, mAddSchedulePaint);
//
//                tempOldScheduleBean = addScheduleTableAreaRect;
//                index++;
//            }
//        }
    }


    /**
     * 通过坐标和移动距离，确定移动方向
     *
     * @param y y轴
     * @return 方向
     */
    private int getDirection(int y) {
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
     * @param dy 偏移量
     */
    private void top(int dy) {
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
     * @param dy 偏移量
     */
    private void bottom(int dy) {
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
