package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;

import butterknife.ButterKnife;

public class CharSequenceCardItemView extends TypefaceTextView implements AdapterView<CharSequence> {

    public CharSequenceCardItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CharSequenceCardItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final CharSequence content) {
        setText(content);
    }

}
