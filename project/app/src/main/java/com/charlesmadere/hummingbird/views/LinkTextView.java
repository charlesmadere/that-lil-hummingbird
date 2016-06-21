package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.misc.CustomLinkMovementMethod;

public class LinkTextView extends TypefaceTextView {

    public LinkTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setMovementMethod(CustomLinkMovementMethod.getInstance());
    }

}
