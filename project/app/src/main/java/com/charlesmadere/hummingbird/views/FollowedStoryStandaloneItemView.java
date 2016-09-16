package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.FollowedStory;

import butterknife.ButterKnife;

public class FollowedStoryStandaloneItemView extends CardView implements AdapterView<FollowedStory> {

    public FollowedStoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedStoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final FollowedStory content) {

    }

}
