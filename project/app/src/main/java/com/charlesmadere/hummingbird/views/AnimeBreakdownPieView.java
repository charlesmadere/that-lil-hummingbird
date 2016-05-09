package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;

import java.text.NumberFormat;

public class AnimeBreakdownPieView extends View {

    private NumberFormat mNumberFormat;
    private Paint mPrimaryPaint;
    private Paint mSecondaryPaint;
    private Paint mTextPaint;
    private RectF mRect;

    private float mBiggest;
    private float mTotal;


    public AnimeBreakdownPieView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public AnimeBreakdownPieView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimeBreakdownPieView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (!ViewCompat.isLaidOut(this) || mBiggest == 0f || mTotal == 0f || mRect.isEmpty()) {
            return;
        }

        final int height = getHeight() - getPaddingBottom() - getPaddingTop();
        final int width = getWidth() - getPaddingLeft() - getPaddingRight();
        final int centerX = width / 2;
        final int centerY = height / 2;

        canvas.drawCircle(centerX, centerY, (width - mSecondaryPaint.getStrokeWidth()) / 2, mSecondaryPaint);
        canvas.drawArc(mRect, 270f, (mBiggest / mTotal) * 360f, false, mPrimaryPaint);
        canvas.drawText(mNumberFormat.format(mBiggest), centerX, centerY, mTextPaint);
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        updateRect();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateRect();
        invalidate();
    }

    private void parseAttributes(final AttributeSet attrs) {
        final Context context = getContext();
        final Resources resources = getResources();

        final TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.AnimeBreakdownPieView);
        final float mBarThickness = ta.getDimension(R.styleable.AnimeBreakdownPieView_barThickness,
                resources.getDimension(R.dimen.pie_stroke_width));
        final int primaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_primaryColor,
                ContextCompat.getColor(context, R.color.orange));
        final int secondaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_secondaryColor,
                ContextCompat.getColor(context, R.color.orangeTranslucent));
        final int textColor = ta.getColor(R.styleable.AnimeBreakdownPieView_textColor,
                ContextCompat.getColor(context, R.color.orange));
        final float textSize = ta.getDimension(R.styleable.AnimeBreakdownPieView_textSize,
                resources.getDimension(R.dimen.text_xxlarge));
        ta.recycle();

        mPrimaryPaint = new Paint();
        mPrimaryPaint.setAntiAlias(true);
        mPrimaryPaint.setColor(primaryColor);
        mPrimaryPaint.setStrokeWidth(mBarThickness);
        mPrimaryPaint.setStyle(Paint.Style.STROKE);

        mSecondaryPaint = new Paint();
        mSecondaryPaint.setAntiAlias(true);
        mSecondaryPaint.setColor(secondaryColor);
        mSecondaryPaint.setStrokeWidth(mBarThickness);
        mSecondaryPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setTextSize(textSize);

        mNumberFormat = NumberFormat.getInstance();
        mRect = new RectF();
    }

    public void setValues(final float total, final float biggest) {
        if (total <= 0f) {
            throw new IllegalArgumentException("total (" + total + ") must be > 0");
        } else if (biggest <= 0f) {
            throw new IllegalArgumentException("biggest (" + biggest + ") must be > 0");
        } else if (biggest > total) {
            throw new IllegalArgumentException("biggest (" + biggest + ") must not be bigger"
                    + " than total (" + total + ')');
        }

        mTotal = total;
        mBiggest = biggest;
        invalidate();
    }

    private void updateRect() {
        mRect.set(0f, 0f, getWidth() - getPaddingLeft() - getPaddingRight(),
                getHeight() - getPaddingTop() - getPaddingBottom());
    }

}
