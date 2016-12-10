package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.PostStory;

import butterknife.ButterKnife;

public class PostStoryItemView extends CardView implements AdapterView<PostStory>,
        View.OnClickListener {




    public PostStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public PostStoryItemView(final Context context, final AttributeSet attrs,
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
    public void setContent(final PostStory content) {

    }

}
