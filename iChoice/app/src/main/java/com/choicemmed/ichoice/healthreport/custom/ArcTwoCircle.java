package com.choicemmed.ichoice.healthreport.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.choicemmed.ichoice.healthreport.utils.DpOrPxUtils;

/**
 * @author Created by Jiang nan on 2019/12/4 11:44.
 * @description
 **/
public class ArcTwoCircle extends View {
    private Paint bigCirclePaint;
    private Paint smallCirclePaint;

    private float barWidth = 14;//圆弧进度条宽度
    private int defaultSize;//自定义View默认的宽高
    private RectF mRectF;//绘制圆弧的矩形区域
     int bigStart = 60;
     int bigEnd = 280;
     int smallStart = 90;
     int smallEnd = 140;
     float small_startAngle;
     float small_sweepAngle;

    public ArcTwoCircle(Context context) {
        super(context);
    }

    public ArcTwoCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ArcTwoCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs) {
        bigCirclePaint = new Paint();
        smallCirclePaint = new Paint();
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setStyle(Paint.Style.STROKE);
        bigCirclePaint.setStrokeWidth(barWidth);
        bigCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        bigCirclePaint.setColor(Color.rgb(192,192,192));
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setStyle(Paint.Style.STROKE);
        smallCirclePaint.setStrokeWidth(barWidth);
        smallCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        smallCirclePaint.setColor(Color.rgb(255,163,18));

        mRectF = new RectF();
        defaultSize = DpOrPxUtils.dip2px(context,150);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形

        if (min >= barWidth * 2) {
            mRectF.set(barWidth / 2, barWidth / 2, min - barWidth / 2, min - barWidth / 2);
        }
    }

    private int measureSize(int defaultSize,int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF,135,270,false,bigCirclePaint);
        compterData();
        canvas.drawArc(mRectF,small_startAngle,small_sweepAngle,false,smallCirclePaint);
    }

    private void compterData() {
        int BigMax = bigEnd - bigStart;
        float one =  270/BigMax;
        small_startAngle = 135 + one * (smallStart - bigStart);
        small_sweepAngle =(smallEnd - smallStart) * one;
        Log.d("angle",small_startAngle+"|"+small_sweepAngle);
    }

    public void setBig(int bigStart,int bigEnd) {
        this.bigStart = bigStart;
        this.bigEnd = bigEnd;
    }

    public void setSmall(int smallStart,int smallEnd) {
        this.smallStart = smallStart;
        this.smallEnd = smallEnd;
    }


}
