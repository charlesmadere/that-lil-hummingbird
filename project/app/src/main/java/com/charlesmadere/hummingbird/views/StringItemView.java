package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;

public class StringItemView extends TypefaceTextView implements AdapterView<String> {

    public StringItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public StringItemView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setContent(final String content) {
        setText(content);
    }

}
