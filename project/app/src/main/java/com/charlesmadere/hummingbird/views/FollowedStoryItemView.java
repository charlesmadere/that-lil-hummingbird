package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedStoryItemView extends CardView implements AdapterView<FollowedStory>,
        View.OnClickListener {

    private FollowedStory mFollowedStory;
    private NumberFormat mNumberFormat;

    @BindView(R.id.fsivZero)
    FollowedSubstoryItemView mSubstoryZero;

    @BindView(R.id.fsivOne)
    FollowedSubstoryItemView mSubstoryOne;

    @BindView(R.id.fsivTwo)
    FollowedSubstoryItemView mSubstoryTwo;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public FollowedStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        // TODO
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

    @Override
    public void setContent(final FollowedStory content) {
        mFollowedStory = content;

        final User user = content.getUser();
        mAvatar.setImageURI(Uri.parse(user.getAvatar()));

        final Resources res = getResources();
        mTitle.setText(res.getString(R.string.x_y, user.getName(),
                res.getQuantityString(R.plurals.followed_x_users, mFollowedStory.getSubstoryCount(),
                        mNumberFormat.format(mFollowedStory.getSubstoryCount()))));
        mTimeAgo.setText(mFollowedStory.getCreatedAt().getRelativeTimeText(getContext()));

        // TODO
    }

}
