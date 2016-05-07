package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.networking.Api;

import java.text.NumberFormat;

public class FavoriteImageButton extends AppCompatImageButton implements View.OnClickListener {

    private CommentStory mStory;
    private NumberFormat mNumberFormat;


    public FavoriteImageButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteImageButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @Override
    public void onClick(final View v) {
        Api.likeStory(mStory);

        if (isActivated()) {
            setActivated(false);

        } else {
            setActivated(true);

        }
    }

    public void setStory(final CommentStory story) {
        mStory = story;
        setActivated(story.isLiked());
    }

}
