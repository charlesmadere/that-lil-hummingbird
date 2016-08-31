package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.GroupMember;
import com.charlesmadere.hummingbird.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupMemberItemView extends FrameLayout implements AdapterView<GroupMember>,
        View.OnClickListener {

    private GroupMember mGroupMember;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.tvBio)
    TextView mBio;

    @BindView(R.id.groupMemberBadge)
    TextView mGroupMemberBadge;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public GroupMemberItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupMemberItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GroupMemberItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mGroupMember.getUserId()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final GroupMember content) {
        mGroupMember = content;

        final User user = mGroupMember.getUser();
        mAvatar.setContent(user);
        mGroupMemberBadge.setText(mGroupMember.getRank().getTextResId());

        mTitle.setText(user.getId());

        if (user.hasBio()) {
            mBio.setText(user.getBio());
            mBio.setVisibility(VISIBLE);
        } else {
            mBio.setVisibility(GONE);
        }
    }

}
