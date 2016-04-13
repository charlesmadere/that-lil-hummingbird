package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class SelectableTextView extends AppCompatTextView {

    private static final Typeface NORMAL = Typeface.DEFAULT;
    private static final Typeface SELECTED;


    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SELECTED = Typeface.create("sans-serif-medium", Typeface.NORMAL);
        } else {
            SELECTED = Typeface.DEFAULT_BOLD;
        }
    }

    public SelectableTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        setTypeface(selected ? SELECTED : NORMAL);
    }

}
