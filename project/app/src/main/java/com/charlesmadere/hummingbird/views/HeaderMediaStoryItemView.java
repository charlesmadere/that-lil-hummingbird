package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.MediaStory;

public class HeaderMediaStoryItemView extends CardView implements AdapterView<MediaStory>,
        View.OnClickListener {

    public HeaderMediaStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderMediaStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View view) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void setContent(final MediaStory content) {

    }

}
