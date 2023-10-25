package com.marshal.moudle.calendar.schedule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.marshal.moudle.calendar.schedule.R;


/**
 * Created by zero_android on 2020/6/29.
 * <p>
 * 一个可以自由拖拽、拉伸的view
 */
public class DragRectView extends View implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG = "DragRectView";

    private static final int TOP = 0x21;
    private static final int BOTTOM = 0x23;
    private static final int CENTER = 0x25;
    private int offset = 50;

    private int dragDirection;
    private int lastX;
    private int lastY;

    // view的宽、高初始化
    private int rectTop = 0;
    private int rectLeft = 0;
    private int width = 0;
    private int height = 0;
    // 线条的宽度
    private int mLineSize = 3;
    // 图片大小
    private int mRectSize = 50;
    private Paint mPaint;
    private Rect mRect;
    private Paint.Style mStyle;
    // 拉伸的view
    private Bitmap mBottomBmp;
    private Rect mBottomRect;
    private Bitmap mTopBmp;
    private Rect mTopRect;
    private int screenWidth;
    private int screenHeight;

    private OnMyTouchListener myTouchListener;

    public DragRectView(Context context) {
        this(context, null);
    }

    public DragRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化view
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Widget_DragRectView);
//        rectLeft = (int) typedArray.getInt(R.styleable.Widget_DragRectView_rect_left, 0);
//        rectTop = (int) typedArray.getInt(R.styleable.Widget_DragRectView_rect_top, 0);
//        width = (int) typedArray.getInt(R.styleable.Widget_DragRectView_rect_right, 20);
//        height = (int) typedArray.getInt(R.styleable.Widget_DragRectView_rect_bottom, 20);
//        height += rectTop;

        setOnTouchListener(this);
        setOnClickListener(this);
        setSelected(true);

        mPaint = new Paint();
        mStyle = Paint.Style.FILL_AND_STROKE;
        mPaint.setColor(getResources().getColor(R.color.select_schedule_bg));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(mStyle);
        mPaint.setStrokeWidth((float) mLineSize);

        mTopBmp = BitmapFactory.decodeResource(getResources(), R.drawable.stretch_bottom);
        mBottomBmp = BitmapFactory.decodeResource(getResources(), R.drawable.stretch_bottom);
        mBottomRect = new Rect();
        mTopRect = new Rect();
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
    }

    @Override
    public void onClick(View v) {
    }

    public interface OnMyTouchListener {
        void onClick();
    }

    public void setMyTouchListener(OnMyTouchListener myTouchListener) {
        this.myTouchListener = myTouchListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //实现拦截外部事件
//        v.getParent().requestDisallowInterceptTouchEvent(true);
        if (myTouchListener != null) {
            myTouchListener.onClick();
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            setSelected(true);
            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();

            dragDirection = getDirection(v, (int) event.getX(), (int) event.getY());
        }

        handleDrag(v, event, action);
        invalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        Log.d("TAG", "drag onDraw left :" + rectLeft);
        Log.d("TAG", "drag onDraw  top :" + rectTop);
        Log.d("TAG", "drag onDraw  right :" + width);
        Log.d("TAG", "drag onDraw  bottom :" + height);

        mRect.set(rectLeft, rectTop, width, height);
        canvas.drawRect(mRect, mPaint);

        canvas.restore();
        if (isSelected()) {
            //取right - left 就是整个长度，然后取值长度的一半，从left起点加上一半
            int left = mRect.left + ((mRect.right - mRect.left) / 2);

            mTopRect.set(left - (mRectSize / 2), mRect.top - (mRectSize / 2), left + (mRectSize / 2), mRect.top + (mRectSize / 2));
            canvas.drawBitmap(mTopBmp, null, mTopRect, this.mPaint);

            mBottomRect.set(left - (mRectSize / 2), mRect.bottom - (mRectSize / 2), left + (mRectSize / 2), mRect.bottom + (mRectSize / 2));
            canvas.drawBitmap(mBottomBmp, null, mBottomRect, this.mPaint);
        }
    }

    private void handleDrag(View v, MotionEvent event, int action) {
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                dragDirection = 0;
//                v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = ((int) event.getRawX()) - lastX;
                int dy = ((int) event.getRawY()) - lastY;
                switch (dragDirection) {
                    case BOTTOM:
                        bottom(v, dy);
                        break;
                    case CENTER:
                        break;
                    case TOP:
                        top(v, dy);
                        break;
                }
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            default:
                break;
        }
    }

    /**
     * 通过坐标和移动距离，确定移动方向
     *
     * @param v
     * @param x
     * @param y
     * @return
     */
    private int getDirection(View v, int x, int y) {
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


    public void setHeights(float height) {
        this.height = (int) (0.5f + height);
    }

    public void setWidths(float width) {
        this.width = (int) (0.5f + width);
    }

    public void setDrawRect(int left, int top, int right, int bottom) {
        this.rectLeft = (int) (0.5f + left);
        this.rectTop = (int) (0.5f + top);
        this.width = (int) (0.5f + right);
        this.height = (int) (0.5f + bottom);

        Log.d("TAG", "drag setDrawRect left :" + rectLeft);
        Log.d("TAG", "drag setDrawRect  top :" + rectTop);
        Log.d("TAG", "drag setDrawRect  right :" + width);
        Log.d("TAG", "drag setDrawRect  bottom :" + height);

        postInvalidate();
    }

    /**
     * 设置滑动顶部位置，位置的数值
     *
     * @param v
     * @param dy
     */
    private void top(View v, int dy) {
        Log.d("TAG", "...top... dy :" + dy);
        Log.d("TAG", "...v.top...  :" + v.getTop());
        rectTop += dy;
        if (rectTop < v.getTop()) {
            rectTop = v.getTop();
        } else if (rectTop > (height - offset)) {
            rectTop = height - offset;
        }
    }

    /**
     * 设置滑动底部位置，位置的数值
     *
     * @param v
     * @param dy
     */
    private void bottom(View v, int dy) {
        Log.d("TAG", "...bottom... dy :" + dy);
        height += dy;
        if (height < (rectTop + offset)) {
            height = rectTop + offset;
        } else if (height > v.getHeight() - offset) {
            height = v.getHeight() - offset;
        }
    }

}