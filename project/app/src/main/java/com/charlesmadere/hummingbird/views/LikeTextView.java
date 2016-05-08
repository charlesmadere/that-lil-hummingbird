package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.networking.Api;

import java.text.NumberFormat;

public class LikeTextView extends TypefaceTextView implements View.OnClickListener {

    private boolean mInitiallyLiked;
    private CommentStory mStory;
    private NumberFormat mNumberFormat;


    public LikeTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LikeTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        mStory.setLiked(!mStory.isLiked());
        Api.likeStory(mStory);
        update();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setContent(final CommentStory story) {
        mStory = story;
        mInitiallyLiked = mStory.isLiked();
        update();
    }

    private void update() {
        setActivated(mStory.isLiked());
        int totalVotes = mStory.getTotalVotes();

        if (isActivated() && !mInitiallyLiked) {
            ++totalVotes;
        } else if (mInitiallyLiked) {
            --totalVotes;
        }

        setText(mNumberFormat.format(totalVotes));
    }

}
