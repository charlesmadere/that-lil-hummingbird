package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;

public final class RatioViewHelper {

    private static final int FIXED_SIDE_HEIGHT = 0;
    private static final int FIXED_SIDE_WIDTH = 1;
    private static final int UNDEFINED = -1;

    private final int fixedSide;
    private final int heightRatio;
    private final int widthRatio;


    public static RatioViewHelper create(final View view, final AttributeSet attrs) {
        final Context context = view.getContext();
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View);
        final RatioViewHelper fixedSide = create(ta);
        ta.recycle();
        return fixedSide;
    }

    public static RatioViewHelper create(final TypedArray ta) {
        final int fixedSide = ta.getInt(R.styleable.View_fixed_side, UNDEFINED);
        final int heightRatio = ta.getInt(R.styleable.View_height_ratio, UNDEFINED);
        final int widthRatio = ta.getInt(R.styleable.View_width_ratio, UNDEFINED);

        if (fixedSide == UNDEFINED || heightRatio == UNDEFINED || widthRatio == UNDEFINED) {
            throw new IllegalArgumentException("fixed_side, height_ratio, and width_ratio"
                    + " must all be set");
        }

        return new RatioViewHelper(fixedSide, heightRatio, widthRatio);
    }

    private RatioViewHelper(final int fixedSide, final int heightRatio, final int widthRatio) {
        this.fixedSide = fixedSide;
        this.heightRatio = heightRatio;
        this.widthRatio = widthRatio;
    }

    public int getHeightRatio() {
        return heightRatio;
    }

    public int getWidthRatio() {
        return widthRatio;
    }

    public boolean isFixedSideHeight() {
        return fixedSide == FIXED_SIDE_HEIGHT;
    }

    public boolean isFixedSideWidth() {
        return fixedSide == FIXED_SIDE_WIDTH;
    }

    /**
     * To be called from your view's {@link View#onMeasure(int, int)} method and then supplied
     * to its {@link View#setMeasuredDimension(int, int)} method.
     *
     * @return
     * An int array where [0] is the width and [1] is the height. These values should be passed
     * in as the parameters to {@link View#setMeasuredDimension(int, int)}.
     */
    public int[] measureDimensions(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int width, height;

        switch (fixedSide) {
            case FIXED_SIDE_HEIGHT:
                height = View.MeasureSpec.getSize(heightMeasureSpec);
                width = (int) ((float) (height * widthRatio) / (float) heightRatio);
                break;

            case FIXED_SIDE_WIDTH:
                width = View.MeasureSpec.getSize(widthMeasureSpec);
                height = (int) ((float) (width * heightRatio) / (float) widthRatio);
                break;

            default:
                // this should never happen
                throw new RuntimeException("unknown fixedSide: " + fixedSide);
        }

        return new int[] { width, height };
    }

    /**
     * to be called from your ViewGroup's {@link android.view.ViewGroup#onMeasure(int, int)}
     * method
     *
     * @return
     * An int array where [0] is the width measure spec and [1] is the height. These values
     * should be passed in as the parameters to {@link android.view.ViewGroup#onMeasure(int, int)}.
     */
    public int[] measureSpecDimensions(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int[] dimensions = measureDimensions(widthMeasureSpec, heightMeasureSpec);
        dimensions[0] = View.MeasureSpec.makeMeasureSpec(dimensions[0], View.MeasureSpec.EXACTLY);
        dimensions[1] = View.MeasureSpec.makeMeasureSpec(dimensions[1], View.MeasureSpec.EXACTLY);

        return dimensions;
    }

}
