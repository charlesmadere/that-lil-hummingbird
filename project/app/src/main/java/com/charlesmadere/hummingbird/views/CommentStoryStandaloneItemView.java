package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.CommentStory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentStoryStandaloneItemView extends CardView implements AdapterView<CommentStory> {

    private CommentStory mCommentStory;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.commentTextView)
    CommentTextView mComment;

    @BindView(R.id.commentTitleTextView)
    CommentTitleTextView mCommentTitleTextView;

    @BindView(R.id.likeTextView)
    LikeTextView mLikeTextView;

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
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final CommentStory content) {
        mCommentStory = content;

        mAvatar.setContent(mCommentStory.getPoster());
        mLikeTextView.setContent(mCommentStory);
        mCommentTitleTextView.setContent(mCommentStory);
        mTimeAgo.setText(mCommentStory.getCreatedAt().getRelativeTimeText(getContext()));
        mComment.setContent(mCommentStory);
    }

}
