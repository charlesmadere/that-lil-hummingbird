package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.FollowedStory;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedStoryStandaloneItemView extends CardView implements AdapterView<FollowedStory> {

    private NumberFormat mNumberFormat;

    @BindView(R.id.avatarView)
    AvatarView mAvatarView;

    @BindView(R.id.tvFollowedCount)
    TextView mFollowedCount;

    @BindView(R.id.tvUsername)
    TextView mUsername;


    public FollowedStoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedStoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final FollowedStory content) {
        mAvatarView.setContent(content.getUser());
        mUsername.setText(content.getUserId());
        mFollowedCount.setText(getResources().getQuantityString(R.plurals.followed_x_users,
                content.getSubstoryCount(), mNumberFormat.format(content.getSubstoryCount())));
    }

}
