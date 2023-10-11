package com.marshal.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * android中 Paint 类就是画笔，
 * 而 Canvas 类就是纸 在这里 作画布
 * <p>
 * paint.setStyle()
 * • Paint.Style.FILL 仅填充内部
 * • Paint.Style.FILL_ AND_ STROKE ：填充内部和描边
 * • Paint.Style.STROKE 仅描边
 * <p>
 * paint.setStrokeWidth()
 * 于设置描边宽度值 单位是 px
 * <p>
 * drawLine
 * 绘制直线
 * <p>
 * 矩形工具类 RectF Rect 概述
 * 这两个类都是矩形工具类，根据 4个点构造出 1个矩形结构。 RectF Rect 中的方
 * 成员变量完全一样，唯一不同的是： RectF 是用来保存 float 类型数值的矩形结构的，而 Rect
 * 是用来保存 int 类型数值 矩形结构的
 * 调用函数者保证 left <= right and top <= bottom
 * <p>
 * drawArgb() 带透明度的绘制颜色
 * drawrgb()  不带透明度的绘制颜色
 * <p>
 * drawPath() 绘制路径
 * Path 代表路径
 * 涉及的函数
 * moveTo(x1,y1) 路径的起始点
 * lineTo(x2,y2) 直线的终点又是下一次绘制路径的起始点，可以一直调用
 * close() 如果起点和终点
 * <p>
 * Region 封闭的区域
 */

public class CustomView extends View {

    //画笔
    private Paint paint = new Paint();

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        //设置画笔颜色
//        paint.setColor(Color.RED);
//        //设置填充样式
//        paint.setStyle(Paint.Style.STROKE);
//        //设置画笔宽度
//        paint.setStrokeWidth(10f);
//        canvas.drawCircle(200,200,50f,paint);

//         paint.setColor(0xffff0000);
//         paint.setStyle(Paint.Style.FILL);
//         paint.setStrokeWidth(50f);
//         canvas.drawCircle(200,200,150,paint);
//         paint.setColor(0x7effff00);
//         canvas.drawCircle(200,200,100,paint);

//          canvas.drawARGB(0xff,0xff,0xff,0xff);
        //绘制画布背景颜色
//            canvas.drawColor(0xffff00ff);
        //绘制直线
//           paint.setColor(Color.RED);
//           paint.setStyle(Paint.Style.FILL_AND_STROKE);
//           paint.setStrokeWidth(50);
        //安卓 view的默认x,y两条轴线都是从0开始，x轴从左到右,y轴从上倒下
//           canvas.drawLine(200,200,400,200,paint);

        //绘制点  drawPoint
//           paint.setColor(Color.RED);
//           paint.setStrokeWidth(50);
        //绘制出一个像素点
//           canvas.drawPoint(200,200,paint);

        //绘制矩形
        //left  绘制顶部的左侧x,y坐标点，top绘制顶部距离屏幕的高度
        //right 绘制顶部的右侧x,y坐标点,bottom绘制距离top的高度
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(50f);
//        Rect rect = new Rect(100,100,200,200);
//        canvas.drawRect(rect,paint);
//        canvas.drawRect(100,300,500,500,paint);

        //绘制路径
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
//        //绘制直角三角形
//        Path path = new Path();
//        path.moveTo(100f,100f);
//        path.lineTo(100f,400f);
//        path.lineTo(700f,400f);
//        path.close();
//        canvas.drawPath(path,paint);
        //绘制弧线
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
//        Path path = new Path();
//        path.moveTo(10, 10);
//        RectF rectF = new RectF(100, 10, 200, 100);
//        path.arcTo(rectF, 0, 90, true);
//        canvas.drawPath(path, paint);

        //绘制region
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.RED);
//        Region region = new Region(new Rect(50, 50, 200, 100));
//        RegionIterator iterator = new RegionIterator(region);
//        Rect r = new Rect();
//        while(iterator.next(r)){
//            canvas.drawRect(r,paint);
//        }

//        canvas.drawColor(Color.GREEN);
//
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
//        //平移画布
//        canvas.translate(100, 100);
//        Rect rect = new Rect(0, 0, 400, 220);
//        canvas.drawRect(rect, paint);

//        canvas.drawColor(Color.RED);
//        canvas.save();
//        canvas.clipRect(100,100,600,600);
//        canvas.drawColor(Color.GREEN);
        //恢复画布
//        canvas.restore();
//        canvas.drawColor(Color.BLUE);



    }
}
