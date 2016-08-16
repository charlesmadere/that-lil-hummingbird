package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.CommentStoryActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ProfileCommentNotification;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileCommentNotificationItemView extends CardView implements
        AdapterView<ProfileCommentNotification>, View.OnClickListener {

    private ProfileCommentNotification mProfileCommentNotification;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.notificationTitleTextView)
    NotificationTitleTextView mNotificationTitleTextView;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public ProfileCommentNotificationItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileCommentNotificationItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void handleStoryClick(final AbsNotification.StorySource source) {
        final AbsStory story = source.getStory();
        final Context context = getContext();

        switch (story.getType()) {
            case COMMENT:
                context.startActivity(CommentStoryActivity.getLaunchIntent(context,
                        (CommentStory) story));
                break;

            default:
                throw new RuntimeException("encountered unknown " + AbsStory.Type.class.getName()
                        + ": \"" + story.getType() + '"');
        }
    }

    @Override
    public void onClick(final View v) {
        final AbsNotification.AbsSource source = mProfileCommentNotification.getSource();

        switch (source.getType()) {
            case STORY:
                handleStoryClick((AbsNotification.StorySource) source);
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
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final ProfileCommentNotification content) {
        mProfileCommentNotification = content;

        mAvatar.setContent(mProfileCommentNotification.getUser());
        mNotificationTitleTextView.setText(mProfileCommentNotification);
        mTimeAgo.setText(mProfileCommentNotification.getCreatedAt().getRelativeTimeText(
                getContext()));
    }

}
