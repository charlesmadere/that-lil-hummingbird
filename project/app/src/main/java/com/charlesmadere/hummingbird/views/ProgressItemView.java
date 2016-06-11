package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.charlesmadere.hummingbird.adapters.AdapterView;

public class ProgressItemView extends FrameLayout implements AdapterView<Void> {

    public ProgressItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressItemView(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setContent(final Void content) {
        // intentionally empty
    }

}
