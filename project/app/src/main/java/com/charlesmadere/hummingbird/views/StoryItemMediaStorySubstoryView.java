package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.BaseUserActivity;
import com.charlesmadere.hummingbird.models.Substory;
import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryItemMediaStorySubstoryView extends FrameLayout implements View.OnClickListener {

    private NumberFormat mNumberFormat;
    private Substory mSubstory;
    private User mUser;

    @BindView(R.id.kvtvAction)
    KeyValueTextView mAction;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public StoryItemMediaStorySubstoryView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryItemMediaStorySubstoryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StoryItemMediaStorySubstoryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Substory getSubstory() {
        return mSubstory;
    }

    public User getUser() {
        return mUser;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(BaseUserActivity.getLaunchIntent(context, mUser));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setContent(final User user, final Substory substory) {
        mUser = user;
        mSubstory = substory;
        mAvatar.setImageURI(Uri.parse(mUser.getAvatarSmall()));
        mTimeAgo.setText(substory.getCreatedAt().getRelativeTimeText(getContext()));

        final String actionText;
        final String username = mUser.getName();
        final Resources res = getResources();

        switch (mSubstory.getType()) {
            case WATCHED_EPISODE:
                actionText = res.getString(R.string.watched_episode_x, mNumberFormat.format(
                        mSubstory.getEpisodeNumber()));
                break;

            case WATCHLIST_STATUS_UPDATE:
                switch (mSubstory.getNewStatus()) {
                    case COMPLETED:
                        actionText = res.getString(R.string.has_completed);
                        break;

                    case CURRENTLY_WATCHING:
                        actionText = res.getString(R.string.is_currently_watching);
                        break;

                    case DROPPED:
                        actionText = res.getString(R.string.has_dropped);
                        break;

                    case ON_HOLD:
                        actionText = res.getString(R.string.has_placed_on_hold);
                        break;

                    case PLAN_TO_WATCH:
                        actionText = res.getString(R.string.plans_to_watch);
                        break;

                    default:
                        throw new RuntimeException("encountered illegal " +
                                Substory.NewStatus.class.getName() + ": " +
                                mSubstory.getNewStatus());
                }
                break;

            default:
                throw new RuntimeException("encountered illegal " +
                        Substory.Type.class.getName() + ": " + mSubstory.getType());

        }

        mAction.setText(username, actionText);
    }

}
