package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.CommentsActivity;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentStoryItemView extends CardView implements AdapterView<CommentStory>,
        View.OnClickListener {

    private CommentStory mCommentStory;

    @BindView(R.id.commentTitleTextView)
    CommentTitleTextView mTitle;

    @BindView(R.id.likeTextView)
    LikeTextView mLikeTextView;

    @BindView(R.id.llReplies)
    LinearLayout mReplies;

    @BindView(R.id.rsivZero)
    ReplySubstoryItemView mReplyZero;

    @BindView(R.id.rsivOne)
    ReplySubstoryItemView mReplyOne;

    @BindView(R.id.rsivTwo)
    ReplySubstoryItemView mReplyTwo;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvComment)
    TextView mComment;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public CommentStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.sdvAvatar)
    void onAvatarClick() {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mCommentStory.getPoster()));
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(CommentsActivity.getLaunchIntent(context, mCommentStory));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final CommentStory content) {
        mCommentStory = content;

        mAvatar.setImageURI(Uri.parse(mCommentStory.getPoster().getAvatarLargest()));
        mLikeTextView.setContent(mCommentStory);
        mTitle.setContent(mCommentStory);
        mTimeAgo.setText(mCommentStory.getCreatedAt().getRelativeTimeText(getContext()));
        mComment.setText(mCommentStory.getComment());

        if (content.hasSubstoryIds()) {
            final ArrayList<AbsSubstory> substories = mCommentStory.getSubstories();
            setReplyView(mReplyZero, substories, 1);
            setReplyView(mReplyOne, substories, 2);
            setReplyView(mReplyTwo, substories, 3);

            mReplies.setVisibility(VISIBLE);
        } else {
            mReplies.setVisibility(GONE);
        }
    }

    private void setReplyView(final ReplySubstoryItemView view,
            final ArrayList<AbsSubstory> substories, final int index) {
        if (substories.size() >= index) {
            view.setContent((ReplySubstory) substories.get(index - 1));
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
    }

}
