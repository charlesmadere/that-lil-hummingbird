package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.networking.Api;

import java.text.NumberFormat;

public class LikeTextView extends TypefaceTextView implements View.OnClickListener {

    private AnimeDigest.Quote mQuote;
    private CommentStory mStory;
    private NumberFormat mNumberFormat;


    public LikeTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LikeTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        update();
    }

    @Override
    public void onClick(final View v) {
        if (mQuote != null) {
            mQuote.toggleFavorite();
            Api.favoriteQuote(mQuote);
            update();
        } else if (mStory != null) {
            mStory.toggleLiked();
            Api.likeStory(mStory);
            update();
        }
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
        mQuote = null;
        mStory = story;
        update();
    }

    public void setContent(final AnimeDigest.Quote quote) {
        mStory = null;
        mQuote = quote;
        update();
    }

    private void update() {
        if (mQuote != null) {
            setActivated(mQuote.isFavorite());
            setText(mNumberFormat.format(mQuote.getFavoriteCount()));
        } else if (mStory != null) {
            setActivated(mStory.isLiked());
            setText(mNumberFormat.format(mStory.getTotalVotes()));
        } else {
            setActivated(false);
            setText(mNumberFormat.format(0));
        }
    }

}
