package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

import java.text.NumberFormat;

public class AnimeBreakdownPieView extends View {

    private NumberFormat mNumberFormat;
    private Paint mPrimaryPaint;
    private Paint mSecondaryPaint;
    private Paint mTextPaint;
    private Rect mTextRect;
    private RectF mPaintRect;

    private float mBiggest;
    private float mTotal;
    private String mBiggestText;


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

        if (!ViewCompat.isLaidOut(this) || mPaintRect.isEmpty() || mBiggest == 0f || mTotal == 0f) {
            return;
        }

        canvas.drawArc(mPaintRect, 270f, 360f, false, mSecondaryPaint);
        canvas.drawArc(mPaintRect, 270f, (mBiggest / mTotal) * 360f, false, mPrimaryPaint);

        canvas.getClipBounds(mTextRect);
        final float cHeight = mTextRect.height();
        final float cWidth = mTextRect.width();
        mTextPaint.getTextBounds(mBiggestText, 0, mBiggestText.length(), mTextRect);

        final float textX = cWidth / 2f - mTextRect.width() / 2f - mTextRect.left;
        final float textY = cHeight / 2f + mTextRect.height() / 2f - mTextRect.bottom;
        canvas.drawText(mBiggestText, textX, textY, mTextPaint);
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        updatePaintRect();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updatePaintRect();
        invalidate();
    }

    private void parseAttributes(final AttributeSet attrs) {
        final Context context = getContext();
        final Resources resources = getResources();

        final TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.AnimeBreakdownPieView);
        final float mBarThickness = ta.getDimension(R.styleable.AnimeBreakdownPieView_barThickness,
                resources.getDimension(R.dimen.anime_breakdown_pie_view_bar_thickness));
        final int primaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_primaryColor,
                MiscUtils.getAttrColor(context, R.attr.colorAccent));
        final int secondaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_secondaryColor,
                MiscUtils.getAttrColor(context, R.attr.colorAccentSecondary));
        final int textColor = ta.getColor(R.styleable.AnimeBreakdownPieView_textColor,
                MiscUtils.getAttrColor(context, R.attr.colorAccent));
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
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(TypefaceStore.get(TypefaceEntry.OPEN_SANS_BOLD));

        mNumberFormat = NumberFormat.getInstance();
        mPaintRect = new RectF();
        mTextRect = new Rect();
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
        mBiggestText = mNumberFormat.format(biggest);
        invalidate();
    }

    private void updatePaintRect() {
        final float strokeWidth = mPrimaryPaint.getStrokeWidth() / 2f;
        mPaintRect.set(getPaddingLeft() + strokeWidth, getPaddingTop() + strokeWidth,
                getWidth() - getPaddingRight() - strokeWidth,
                getHeight() - getPaddingBottom() - strokeWidth);
    }

}
