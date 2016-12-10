package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.CommentStoryV3;

import butterknife.ButterKnife;

public class CommentStoryV3ItemView extends CardView implements AdapterView<CommentStoryV3>,
        View.OnClickListener {




    public CommentStoryV3ItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentStoryV3ItemView(final Context context, final AttributeSet attrs,
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
    public void setContent(final CommentStoryV3 content) {

    }

}
