package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.SearchBundle;

public class SearchResultTypeItemView extends TypefaceTextView implements
        AdapterView<SearchBundle.AbsResult.Type> {

    public SearchResultTypeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchResultTypeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setContent(final SearchBundle.AbsResult.Type content) {
        setText(content.getTextResId());
    }

}
