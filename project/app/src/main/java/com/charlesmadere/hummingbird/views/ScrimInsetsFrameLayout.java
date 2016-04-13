package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.charlesmadere.hummingbird.R;

/**
 * A stripped-down duplicate of the design library's ScrimInsetsFrameLayout. This is necessary
 * because that class is an internal (hidden) View in the design library.
 *
 * This view helps facilitate the transparent status bar that is utilized in Lollipop and beyond.
 *
 * https://stackoverflow.com/questions/28804551/what-is-scriminsetsframelayout
 * http://stackoverflow.com/a/27153313/823952
 */
public class ScrimInsetsFrameLayout extends FrameLayout implements OnApplyWindowInsetsListener {

    private Drawable insetForeground;
    private Rect insets;
    private Rect tempRect;


    public ScrimInsetsFrameLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ScrimInsetsFrameLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrimInsetsFrameLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    @Override
    public void draw(final Canvas canvas) {
        super.draw(canvas);

        if (insets != null) {
            final int width = getWidth();
            final int height = getHeight();
            final int saveCount = canvas.save();

            tempRect.set(0, 0, width, insets.top);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            tempRect.set(0, height - insets.bottom, width, height);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            tempRect.set(0, insets.top, insets.left, height - insets.bottom);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            tempRect.set(width - insets.right, insets.top, width, height - insets.bottom);
            insetForeground.setBounds(tempRect);
            insetForeground.draw(canvas);

            canvas.restoreToCount(saveCount);
        }
    }

    private void initialize() {
        insetForeground = new ColorDrawable(ContextCompat.getColor(getContext(),
                R.color.scrimInsetForeground));
        tempRect = new Rect();
        setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener(this, this);
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(final View v, final WindowInsetsCompat insets) {
        if (this.insets == null) {
            this.insets = new Rect();
        }

        this.insets.set(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
        setWillNotDraw(this.insets.isEmpty());
        postInvalidateOnAnimation();
        return insets.consumeSystemWindowInsets();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        insetForeground.setCallback(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        insetForeground.setCallback(null);
    }

}
