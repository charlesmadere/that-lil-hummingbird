package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.RatedStory;

import butterknife.ButterKnife;

public class RatedStoryItemView extends CardView implements AdapterView<RatedStory>,
        View.OnClickListener {




    public RatedStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public RatedStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View view) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final RatedStory content) {

    }

}
