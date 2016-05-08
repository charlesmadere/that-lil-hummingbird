package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.networking.Api;

import java.text.NumberFormat;
import java.util.ArrayList;

public class LikeTextView extends TypefaceTextView implements View.OnClickListener {

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
        update();
    }

    private void update() {
        setActivated(mStory.isLiked());
        int totalVotes = mStory.getTotalVotes();

        if (mStory.hasRecentLikerIds()) {
            final ArrayList<String> likerIds = mStory.getRecentLikerIds();

            if (likerIds.contains(CurrentUser.get().getId()) && !isActivated()) {
                --totalVotes;
            }
        } else if (isActivated()) {
            ++totalVotes;
        }

        setText(mNumberFormat.format(totalVotes));
    }

}
