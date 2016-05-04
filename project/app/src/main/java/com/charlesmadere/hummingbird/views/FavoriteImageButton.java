package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.View;

public class FavoriteImageButton extends AppCompatImageButton implements View.OnClickListener {

    // TODO
    // Analyze the like / unlike API to find what endpoint this view should be hitting
    // and what data we need to hit it.


    public FavoriteImageButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteImageButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        // TODO
    }

}
