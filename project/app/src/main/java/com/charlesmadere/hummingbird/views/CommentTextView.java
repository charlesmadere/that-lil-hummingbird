package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.models.CommentStory;

public class CommentTextView extends LinkTextView {

    public CommentTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(final CommentStory content) {
        setText(buildLinkSpan(content.getComment()));
    }

}
