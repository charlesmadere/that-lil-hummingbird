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

    private float mBarThickness;
    private NumberFormat mNumberFormat;
    private Paint mPrimaryPaint;
    private Paint mSecondaryPaint;
    private Paint mTextPaint;
    private RectF mRect;

    private int mBiggest;
    private int mTotal;


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

        if (!ViewCompat.isLaidOut(this) || mBiggest == 0 || mTotal == 0 || mRect.isEmpty()) {
            return;
        }

        canvas.drawArc(mRect, 0f, 360f, true, mPrimaryPaint);
        canvas.drawText(mNumberFormat.format(mBiggest), getWidth() / 2, getHeight() / 2,
                mTextPaint);
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        updateRects();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateRects();
        invalidate();
    }

    private void parseAttributes(final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        final Context context = getContext();
        final Resources resources = getResources();

        final TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.AnimeBreakdownPieView);
        mBarThickness = ta.getDimension(R.styleable.AnimeBreakdownPieView_barThickness,
                resources.getDimension(R.dimen.root_padding));
        final int primaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_primaryColor,
                ContextCompat.getColor(context, R.color.orange));
        final int secondaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_secondaryColor,
                ContextCompat.getColor(context, R.color.orangeTranslucent));
        final int textColor = ta.getColor(R.styleable.AnimeBreakdownPieView_textColor,
                ContextCompat.getColor(context, R.color.orange));
        final float textSize = ta.getDimension(R.styleable.AnimeBreakdownPieView_textSize,
                resources.getDimension(R.dimen.text_large));
        ta.recycle();

        mPrimaryPaint = new Paint();
        mPrimaryPaint.setAntiAlias(true);
        mPrimaryPaint.setColor(primaryColor);
        mPrimaryPaint.setStyle(Paint.Style.FILL);

        mSecondaryPaint = new Paint();
        mSecondaryPaint.setAntiAlias(true);
        mSecondaryPaint.setColor(secondaryColor);
        mSecondaryPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);

        mNumberFormat = NumberFormat.getInstance();
        mRect = new RectF();
    }

    public void setValues(final int total, final int biggest) {
        if (total <= 0) {
            throw new IllegalArgumentException("total (" + total + ") must be > 0");
        } else if (biggest <= 0) {
            throw new IllegalArgumentException("biggest (" + biggest + ") must be > 0");
        } else if (biggest > total) {
            throw new IllegalArgumentException("biggest (" + biggest + ") must not be bigger"
                    + " than total (" + total + ')');
        }

        mTotal = total;
        mBiggest = biggest;
        invalidate();
    }

    private void updateRects() {
        mRect.set(0f, 0f, getWidth(), getHeight());
    }

}
