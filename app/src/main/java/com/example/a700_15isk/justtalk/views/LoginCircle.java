package com.example.a700_15isk.justtalk.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.a700_15isk.justtalk.R;

/**
 * Created by 700-15isk on 2017/8/15.
 */

public class LoginCircle extends View {
    private int mColor;
    private Paint mPaint = new Paint();
    private float cx, cy;
    private float radius = 100;


    public LoginCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginCircle);
        mColor = typedArray.getColor(R.styleable.LoginCircle_mColor, Color.RED);
        typedArray.recycle();
        init();
    }


    public LoginCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginCircle);
        mColor = typedArray.getColor(R.styleable.LoginCircle_mColor, Color.RED);
        typedArray.recycle();
        init();

    }

    public LoginCircle(Context context) {
        super(context);
    }


    public float getCx() {

        return cx;
    }

    public void setCx(float cx) {
        postInvalidate();
        this.cx = cx;

    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
        postInvalidate();
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        postInvalidate();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        int width = getWidth() ;
        int height = getHeight() ;
        canvas.drawRoundRect(width / 2 - cx, height / 2 - cy, cx + (width / 2), cy + (height / 2), radius, radius, mPaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        cx = getMeasuredWidth() / 2;
        cy = getMeasuredHeight() / 2;

        if (widthMeasureSpecMode == MeasureSpec.AT_MOST && heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);

        } else if (widthMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightMeasureSpecSize);
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSpecSize, 200);
        }

    }


    private void init() {
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }
}
