package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
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

        if (isInEditMode()) {
            return;
        }

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
        final boolean liked;
        final int count;

        if (mQuote != null) {
            liked = mQuote.isFavorite();
            count = mQuote.getFavoriteCount();
        } else if (mStory != null) {
            liked = mStory.isLiked();
            count = mStory.getTotalVotes();
        } else {
            liked = false;
            count = 0;
        }

        setCompoundDrawablesRelativeWithIntrinsicBounds(liked ? R.drawable.ic_favorite_orange_18dp
                : R.drawable.ic_favorite_border_18dp, 0, 0, 0);
        setText(mNumberFormat.format(count));
    }

}
