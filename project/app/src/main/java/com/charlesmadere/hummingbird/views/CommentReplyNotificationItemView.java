package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentReplyNotification;
import com.charlesmadere.hummingbird.models.ReplySubstory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentReplyNotificationItemView extends CardView implements
        AdapterView<CommentReplyNotification>, View.OnClickListener {

    private CommentReplyNotification mCommentReplyNotification;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.notificationTitleTextView)
    NotificationTitleTextView mNotificationTitleTextView;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public CommentReplyNotificationItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentReplyNotificationItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void handleSubstoryAvatarClick(final AbsNotification.SubstorySource source) {
        final AbsSubstory story = source.getSubstory();
        final Context context = getContext();

        switch (story.getType()) {
            case REPLY:
                context.startActivity(UserActivity.getLaunchIntent(context,
                        ((ReplySubstory) story).getUser()));
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsSubstory.Type.class.getName() + ": \"" + story.getType() + '"');
        }
    }

    private void handleSubstoryClick(final AbsNotification.SubstorySource source) {
        final AbsSubstory story = source.getSubstory();
        final Context context = getContext();

        switch (story.getType()) {
            case REPLY:
                // TODO open CommentsActivity
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsSubstory.Type.class.getName() + ": \"" + story.getType() + '"');
        }
    }

    @OnClick(R.id.avatarView)
    void onAvatarClick() {
        final AbsNotification.AbsSource source = mCommentReplyNotification.getSource();

        switch (source.getType()) {
            case SUBSTORY:
                handleSubstoryAvatarClick((AbsNotification.SubstorySource) source);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsNotification.AbsSource.Type.class.getName() + ": \"" +
                        source.getType() + '"');
        }
    }

    @Override
    public void onClick(final View v) {
        final AbsNotification.AbsSource source = mCommentReplyNotification.getSource();

        switch (source.getType()) {
            case SUBSTORY:
                handleSubstoryClick((AbsNotification.SubstorySource) source);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsNotification.AbsSource.Type.class.getName() + ": \"" +
                        source.getType() + '"');
        }
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
    public void setContent(final CommentReplyNotification content) {
        mCommentReplyNotification = content;

        mAvatar.setContent(mCommentReplyNotification.getUser());
        mNotificationTitleTextView.setText(mCommentReplyNotification);
        mTimeAgo.setText(mCommentReplyNotification.getCreatedAt().getRelativeTimeText(
                getContext()));
    }

}
