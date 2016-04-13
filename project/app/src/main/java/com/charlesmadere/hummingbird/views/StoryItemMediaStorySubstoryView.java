package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryItemMediaStorySubstoryView extends FrameLayout implements View.OnClickListener {

    @Bind(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @Bind(R.id.tvAction)
    TextView mAction;

    @Bind(R.id.tvTimeAgo)
    TextView mTimeAgo;

    private ForegroundColorSpan mAccentColorSpan;
    private NumberFormat mNumberFormat;
    private Substory mSubstory;
    private User mUser;


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

    private ForegroundColorSpan getAccentColorSpan() {
        if (mAccentColorSpan == null) {
            mAccentColorSpan = new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                    R.color.colorAccent));
        }

        return mAccentColorSpan;
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
                actionText = res.getString(R.string.x_watched_episode_y, username,
                        mNumberFormat.format(mSubstory.getEpisodeNumber()));
                break;

            case WATCHLIST_STATUS_UPDATE:
                switch (mSubstory.getNewStatus()) {
                    case COMPLETED:
                        actionText = res.getString(R.string.x_has_completed, username);
                        break;

                    case CURRENTLY_WATCHING:
                        actionText = res.getString(R.string.x_is_currently_watching, username);
                        break;

                    case DROPPED:
                        actionText = res.getString(R.string.x_has_dropped, username);
                        break;

                    case ON_HOLD:
                        actionText = res.getString(R.string.x_has_placed_on_hold, username);
                        break;

                    case PLAN_TO_WATCH:
                        actionText = res.getString(R.string.x_plans_to_watch, username);
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

        final SpannableString actionSpannable = new SpannableString(actionText);
        actionSpannable.setSpan(getAccentColorSpan(), 0, username.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAction.setText(actionSpannable);
    }

}
