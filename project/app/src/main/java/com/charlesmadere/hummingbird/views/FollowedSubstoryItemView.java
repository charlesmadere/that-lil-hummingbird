package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.models.FollowedSubstory;
import com.charlesmadere.hummingbird.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedSubstoryItemView extends LinearLayout implements View.OnClickListener {

    private FollowedSubstory mFollowedSubstory;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.tvUsername)
    TextView mUsername;


    public FollowedSubstoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FollowedSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mFollowedSubstory.getUser()));
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

    public void setContent(final FollowedSubstory content) {
        mFollowedSubstory = content;

        final User user = mFollowedSubstory.getUser();
        mAvatar.setContent(user);
        mUsername.setText(user.getId());
    }

}
