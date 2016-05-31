package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.charlesmadere.hummingbird.preferences.Preferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentStoryItemView extends CardView implements AdapterView<CommentStory>,
        View.OnClickListener, View.OnLongClickListener {

    private CommentStory mCommentStory;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.commentTextView)
    CommentTextView mComment;

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

    @BindView(R.id.tvNsfwContent)
    TextView mNsfwContent;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public CommentStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.avatarView)
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
        ButterKnife.bind(this);
    }

    @Override
    public boolean onLongClick(final View v) {
        if (mNsfwContent.getVisibility() == VISIBLE) {
            final CommentStory commentStory = mCommentStory;

            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.this_comment_is_nsfw)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            commentStory.setAdultBypassed(true);

                            if (commentStory == mCommentStory) {
                                setContent(commentStory);
                            }
                        }
                    })
                    .show();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setContent(final CommentStory content) {
        mCommentStory = content;

        mAvatar.setContent(mCommentStory.getPoster());
        mLikeTextView.setContent(mCommentStory);
        mTitle.setContent(mCommentStory);
        mTimeAgo.setText(mCommentStory.getCreatedAt().getRelativeTimeText(getContext()));

        if (mCommentStory.isAdult() && !mCommentStory.isAdultBypassed() &&
                !Boolean.TRUE.equals(Preferences.General.ShowNsfwContent.get())) {
            mComment.setVisibility(GONE);
            mReplies.setVisibility(GONE);
            mNsfwContent.setVisibility(VISIBLE);
            setOnClickListener(null);
            setClickable(false);
            setOnLongClickListener(this);
        } else {
            mNsfwContent.setVisibility(GONE);
            setOnLongClickListener(null);
            setLongClickable(false);
            setOnClickListener(this);

            mComment.setContent(mCommentStory);
            mComment.setVisibility(VISIBLE);

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
