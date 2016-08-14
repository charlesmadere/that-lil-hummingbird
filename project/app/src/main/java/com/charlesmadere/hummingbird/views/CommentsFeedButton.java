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

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsFeedButton extends FrameLayout implements AdapterView<CommentStory>,
        View.OnClickListener {

    private CommentStory mCommentStory;
    private NumberFormat mNumberFormat;

    @BindView(R.id.tvCommentsFeedButton)
    TextView mLabel;


    public CommentsFeedButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentsFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentsFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View view) {

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
        mLabel.setText(getResources().getQuantityString(R.plurals.x_comments,
                mCommentStory.getSubstoryCount(), mNumberFormat.format(
                        mCommentStory.getSubstoryCount())));
    }

}
