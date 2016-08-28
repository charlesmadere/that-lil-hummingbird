package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.StoryActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.CommentReplyNotification;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentReplyNotificationItemView extends FrameLayout implements
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentReplyNotificationItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(StoryActivity.getNotificationIdLaunchIntent(context,
                mCommentReplyNotification.getId()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
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
