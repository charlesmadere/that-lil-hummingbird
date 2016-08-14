package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.networking.Api;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikesFeedButton extends FrameLayout implements AdapterView<CommentStory>,
        View.OnClickListener {

    private CommentStory mCommentStory;
    private NumberFormat mNumberFormat;

    @BindView(R.id.tvLikesFeedButton)
    TextView mLabel;


    public LikesFeedButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LikesFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LikesFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        update();
    }

    @Override
    public void onClick(final View view) {
        if (mCommentStory != null) {
            mCommentStory.toggleLiked();
            Api.likeStory(mCommentStory);
            update();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final CommentStory content) {
        mCommentStory = content;
        update();
    }

    private void update() {
        final boolean liked;
        final int likes;

        if (mCommentStory != null) {
            liked = mCommentStory.isLiked();
            likes = mCommentStory.getTotalVotes();
        } else {
            liked = false;
            likes = 0;
        }

        mLabel.setCompoundDrawablesRelativeWithIntrinsicBounds(liked ?
                R.drawable.ic_favorite_orange_18dp : R.drawable.ic_favorite_border_18dp, 0, 0, 0);
        mLabel.setText(getResources().getQuantityString(R.plurals.x_likes, likes,
                mNumberFormat.format(likes)));
    }

}
