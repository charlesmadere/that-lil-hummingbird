package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;

public class AnimeBreakdownPieView extends View {

    private float mBarThickness;
    private int mPrimaryColor;
    private int mSecondaryColor;


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

        // TODO
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void parseAttributes(final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        final Context context = getContext();
        final TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.AnimeBreakdownPieView);
        mBarThickness = ta.getDimension(R.styleable.AnimeBreakdownPieView_barThickness,
                getResources().getDimension(R.dimen.root_padding));
        mPrimaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_primaryColor,
                ContextCompat.getColor(context, R.color.orange));
        mSecondaryColor = ta.getColor(R.styleable.AnimeBreakdownPieView_secondaryColor,
                ContextCompat.getColor(context, R.color.orangeTranslucent));
        ta.recycle();
    }

}
