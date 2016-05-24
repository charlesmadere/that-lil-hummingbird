package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaSubstoryItemView extends RelativeLayout {

    private NumberFormat mNumberFormat;
    private User mUser;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.kvtvAction)
    KeyValueTextView mAction;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public MediaSubstoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MediaSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @OnClick(R.id.avatarView)
    void onAvatarClick() {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mUser));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setContent(final AbsSubstory content, final User user) {
        mUser = user;
        mAvatar.setContent(user);
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));

        switch (content.getType()) {
            case WATCHED_EPISODE:
                setContent((WatchedEpisodeSubstory) content, user);
                break;

            case WATCHLIST_STATUS_UPDATE:
                setContent((WatchlistStatusUpdateSubstory) content, user);
                break;

            default:
                throw new IllegalArgumentException("encountered illegal " +
                        AbsSubstory.Type.class.getName() + ": \"" + content.getType() + '"');
        }
    }

    private void setContent(final WatchedEpisodeSubstory content, final User user) {
        mAction.setText(user.getId(), getResources().getString(R.string.watched_episode_x,
                mNumberFormat.format(content.getEpisodeNumber())));
    }

    private void setContent(final WatchlistStatusUpdateSubstory content, final User user) {
        mAction.setText(user.getId(), getResources().getString(
                content.getNewStatus().getTextResId()));
    }

}
