package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;

public class CharSequenceItemView extends TypefaceTextView implements AdapterView<CharSequence> {

    public CharSequenceItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CharSequenceItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setContent(final CharSequence content) {
        setText(content);
    }

}
