package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentStoryStandaloneItemView extends CardView implements AdapterView<CommentStory> {

    @BindView(R.id.commentTitleTextView)
    CommentTitleTextView mCommentTitleTextView;

    @BindView(R.id.likeTextView)
    LikeTextView mLikeTextView;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvComment)
    TextView mComment;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public CommentStoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentStoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final CommentStory content) {
        mAvatar.setImageURI(Uri.parse(content.getUser().getAvatar()));
        mLikeTextView.setContent(content);

        if (content.hasGroupId()) {
            mCommentTitleTextView.setText(content.getUser(), content.getGroup());
        } else {
            mCommentTitleTextView.setText(content.getUser());
        }

        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));
        mComment.setText(content.getComment());
    }

}
