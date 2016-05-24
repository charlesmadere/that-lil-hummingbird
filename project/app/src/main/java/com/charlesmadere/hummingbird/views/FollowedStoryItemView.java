package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.FollowedSubstory;
import com.charlesmadere.hummingbird.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedStoryItemView extends CardView implements AdapterView<FollowedStory> {

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.fsttvTitle)
    FollowedStoryTitleTextView mTitle;

    @BindView(R.id.fsivZero)
    FollowedSubstoryItemView mFollowedZero;

    @BindView(R.id.fsivOne)
    FollowedSubstoryItemView mFollowedOne;

    @BindView(R.id.fsivTwo)
    FollowedSubstoryItemView mFollowedTwo;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public FollowedStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public void setContent(final FollowedStory content) {
        final User user = content.getUser();
        mAvatar.setContent(user);
        mTitle.setText(user.getId(), content.getSubstoryCount());
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));

        final ArrayList<AbsSubstory> substories = content.getSubstories();
        mFollowedZero.setContent((FollowedSubstory) substories.get(0));

        if (substories.size() >= 2) {
            mFollowedOne.setContent((FollowedSubstory) substories.get(1));
            mFollowedOne.setVisibility(VISIBLE);
        } else {
            mFollowedOne.setVisibility(GONE);
        }

        if (substories.size() >= 3) {
            mFollowedTwo.setContent((FollowedSubstory) substories.get(2));
            mFollowedTwo.setVisibility(VISIBLE);
        } else {
            mFollowedTwo.setVisibility(GONE);
        }
    }

}
