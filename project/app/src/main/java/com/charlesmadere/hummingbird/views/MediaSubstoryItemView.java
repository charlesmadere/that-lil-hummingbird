package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaSubstoryItemView extends FrameLayout {

    private NumberFormat mNumberFormat;

    @BindView(R.id.kvtvAction)
    KeyValueTextView mAction;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setContent(final AbsSubstory content, final AbsUser user) {
        mAvatar.setImageURI(Uri.parse(user.getAvatarSmall()));
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

    private void setContent(final WatchedEpisodeSubstory content, final AbsUser user) {
        mAction.setText(user.getName(), getResources().getString(R.string.watched_episode_x,
                mNumberFormat.format(content.getEpisodeNumber())));
    }

    private void setContent(final WatchlistStatusUpdateSubstory content, final AbsUser user) {
        mAction.setText(user.getName(), getResources().getString(
                content.getNewStatus().getTextResId()));
    }

}
