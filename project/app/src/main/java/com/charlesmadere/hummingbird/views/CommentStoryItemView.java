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
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.preferences.Preferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentStoryItemView extends CardView implements AdapterView<CommentStory>,
        View.OnClickListener {

    private CommentStory mCommentStory;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.commentTextView)
    CommentTextView mComment;

    @BindView(R.id.commentTitleTextView)
    CommentTitleTextView mTitle;

    @BindView(R.id.llReplies)
    LinearLayout mReplies;

    @BindView(R.id.rsivZero)
    ReplySubstoryItemView mReplyZero;

    @BindView(R.id.rsivOne)
    ReplySubstoryItemView mReplyOne;

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

    private void commentClick() {
        final Context context = getContext();
        context.startActivity(CommentsActivity.getLaunchIntent(context, mCommentStory));
    }

    private void nsfwClick() {
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
    }

    @Override
    public void onClick(final View v) {
        if (mNsfwContent.getVisibility() == VISIBLE) {
            nsfwClick();
        } else {
            commentClick();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final CommentStory content) {
        mCommentStory = content;

        mAvatar.setContent(mCommentStory.getPoster());
        mTitle.setContent(mCommentStory);
        mTimeAgo.setText(mCommentStory.getCreatedAt().getRelativeTimeText(getContext()));

        if (mCommentStory.isAdult() && !mCommentStory.isAdultBypassed() &&
                !Boolean.TRUE.equals(Preferences.General.ShowNsfwContent.get())) {
            mComment.setVisibility(GONE);
            mReplies.setVisibility(GONE);
            mNsfwContent.setVisibility(VISIBLE);
        } else {
            mNsfwContent.setVisibility(GONE);
            mComment.setContent(mCommentStory);
            mComment.setVisibility(VISIBLE);

            if (content.hasSubstoryIds()) {
                final ArrayList<AbsSubstory> substories = mCommentStory.getSubstories();
                setReplyView(mReplyZero, substories, 1);
                setReplyView(mReplyOne, substories, 2);

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
