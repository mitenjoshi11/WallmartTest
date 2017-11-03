package com.wallmarttest.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.wallmarttest.R;


public class TimelineLayout extends LinearLayout {
    private Context mContext;

    private int mLineMarginLeft;
    private int mLineMarginTop;
    private int mLineStrokeWidth;
    private int mLineColor;;
    private int mPointSize;
    private int mPointColor;
    private Bitmap mIcon;

    private Paint mLinePaint;  // Line brushes
    private Paint mPointPaint;  // Point pen


    // First point position
    private int mFirstX;
    private int mFirstY;
    // Last icon position
    private int mLastX;
    private int mLastY;

    public TimelineLayout(Context context) {
        this(context, null);
    }

    public TimelineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimelineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimelineLayout);
        mLineMarginLeft = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_line_margin_left, 10);
        mLineMarginTop = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_line_margin_top, 0);
        mLineStrokeWidth = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_line_stroke_width, 2);
        mLineColor = ta.getColor(R.styleable.TimelineLayout_line_color, 0xff3dd1a5);
        mPointSize = ta.getDimensionPixelSize(R.styleable.TimelineLayout_point_size, 30);
        mPointColor = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_point_color, 0xff000000);

        int iconRes = ta.getResourceId(R.styleable.TimelineLayout_icon_src, R.drawable.ic_dot);
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(iconRes);
        if (drawable != null) {
            mIcon = drawable.getBitmap();
        }

        ta.recycle();

        setWillNotDraw(false);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineStrokeWidth);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setColor(mPointColor);
        mPointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTimeline(canvas);
    }

    private void drawTimeline(Canvas canvas) {
        int childCount = getChildCount();

        if (childCount > 0) {
            if (childCount > 1) {
                // Greater than 1, to prove that there are at least 2, that is, between the first and the second connected to the line, the first and the last one and Icon
                drawFirstPoint(canvas);
                drawLastIcon(canvas);
                drawBetweenLine(canvas);
            } else if (childCount == 1) {
                drawFirstPoint(canvas);
            }
        }
    }

    private void drawFirstPoint(Canvas canvas) {
        View child = getChildAt(0);
        if (child != null) {
            int top = child.getTop();
            mFirstX = mLineMarginLeft;
            mFirstY = top + child.getPaddingTop() + mLineMarginTop;

            // Draw a circle
            canvas.drawCircle(mFirstX, mFirstY, mPointSize, mPointPaint);
        }
    }

    private void drawLastIcon(Canvas canvas) {
        View child = getChildAt(getChildCount() - 1);
        if (child != null) {
            int top = child.getTop();
            mLastX = mLineMarginLeft;
            mLastY = top + child.getPaddingTop() + mLineMarginTop;

            // Drawing
            canvas.drawCircle(mFirstX, mLastY, mPointSize, mPointPaint);
           // canvas.drawBitmap(mIcon, mLastX - (mIcon.getWidth() >> 1), mLastY, null);
        }
    }

    private void drawBetweenLine(Canvas canvas) {
        // Draw a line from the beginning to the end
        canvas.drawLine(mFirstX, mFirstY, mLastX, mLastY, mLinePaint);
        for (int i = 0; i < getChildCount() - 1; i++) {
            // Draw a circle
            int top = getChildAt(i).getTop();
            int y = top + getChildAt(i).getPaddingTop() + mLineMarginTop;
            canvas.drawCircle(mFirstX, y, mPointSize, mPointPaint);
        }
    }

    public int getLineMarginLeft() {
        return mLineMarginLeft;
    }

    public void setLineMarginLeft(int lineMarginLeft) {
        this.mLineMarginLeft = lineMarginLeft;
        invalidate();
    }
}