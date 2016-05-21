package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ProfileCommentNotification;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileCommentNotificationItemView extends CardView implements
        AdapterView<ProfileCommentNotification> {

    private ProfileCommentNotification mProfileCommentNotification;

    @BindView(R.id.notificationTitleTextView)
    NotificationTitleTextView mNotificationTitleTextView;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public ProfileCommentNotificationItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileCommentNotificationItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void handleStoryAvatarClick(final AbsNotification.StorySource source) {
        final AbsStory story = source.getStory();
        final Context context = getContext();

        switch (story.getType()) {
            case COMMENT:
                context.startActivity(UserActivity.getLaunchIntent(context,
                        ((CommentStory) story).getPoster()));
                break;

            default:
                throw new RuntimeException("encountered unknown " + AbsStory.Type.class.getName()
                        + ": \"" + story.getType() + '"');
        }
    }

    @OnClick(R.id.sdvAvatar)
    void onAvatarClick() {
        final AbsNotification.AbsSource source = mProfileCommentNotification.getSource();

        switch (source.getType()) {
            case STORY:
                handleStoryAvatarClick((AbsNotification.StorySource) source);
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
    }

    @Override
    public void setContent(final ProfileCommentNotification content) {
        mProfileCommentNotification = content;

        mAvatar.setImageURI(Uri.parse(mProfileCommentNotification.getUser().getAvatarLargest()));
        mNotificationTitleTextView.setText(mProfileCommentNotification);
        mTimeAgo.setText(mProfileCommentNotification.getCreatedAt().getRelativeTimeText(
                getContext()));
    }

}
