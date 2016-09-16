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
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.FollowedSubstory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedSubstoryStandaloneItemView extends LinearLayout implements
        AdapterView<Void>, View.OnClickListener {

    private FollowedSubstory mFollowedSubstory;
    private int mBottomMargin;

    @BindView(R.id.avatarView)
    AvatarView mAvatarView;

    @BindView(R.id.tvUsername)
    TextView mUsername;

    @BindView(R.id.divider)
    View mDivider;


    public FollowedSubstoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedSubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FollowedSubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
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
        ButterKnife.bind(this);
        setOnClickListener(this);
        mBottomMargin = getResources().getDimensionPixelSize(R.dimen.root_padding_half);
    }

    public void setContent(final FollowedSubstory content, final boolean showDivider) {
        mFollowedSubstory = content;
        mAvatarView.setContent(mFollowedSubstory.getUser());
        mUsername.setText(mFollowedSubstory.getUserId());

        final MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();

        if (showDivider) {
            mDivider.setVisibility(VISIBLE);
            params.bottomMargin = 0;
        } else {
            mDivider.setVisibility(GONE);
            params.bottomMargin = mBottomMargin;
        }

        setLayoutParams(params);
    }

    @Override
    public void setContent(final Void content) {
        // intentionally empty
    }

}
