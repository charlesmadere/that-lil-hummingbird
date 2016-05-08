package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.networking.Api;

import java.text.NumberFormat;

public class LikeTextView extends TypefaceTextView implements View.OnClickListener {

    private boolean mInitiallyLiked;
    private CommentStory mCommentStory;
    private NumberFormat mNumberFormat;


    public LikeTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LikeTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        mCommentStory.setLiked(!mCommentStory.isLiked());
        Api.likeStory(mCommentStory);
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

    public void setCommentStory(final CommentStory commentStory) {
        mCommentStory = commentStory;
        mInitiallyLiked = mCommentStory.isLiked();
        update();
    }

    private void update() {
        setActivated(mCommentStory.isLiked());

        if (isActivated()) {
            if (mInitiallyLiked) {
                setText(mNumberFormat.format(mCommentStory.getTotalVotes()));
            } else {
                setText(mNumberFormat.format(mCommentStory.getTotalVotes() + 1));
            }
        } else {
            if (mInitiallyLiked) {
                setText(mNumberFormat.format(mCommentStory.getTotalVotes() - 1));
            } else {
                setText(mNumberFormat.format(mCommentStory.getTotalVotes()));
            }
        }
    }

}
