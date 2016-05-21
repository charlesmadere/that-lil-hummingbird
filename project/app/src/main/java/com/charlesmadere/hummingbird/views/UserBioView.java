package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBioView extends CardView implements AdapterView<UserDigest> {

    @BindView(R.id.tvBio)
    TextView mBio;

    @BindView(R.id.tvNoDetails)
    TextView mNoDetails;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public UserBioView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public UserBioView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
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
    public void setContent(final UserDigest content) {
        final User user = content.getUser();
        mTitle.setText(getResources().getString(R.string.bio_for_x, user.getId()));

        if (user.hasBio()) {
            mNoDetails.setVisibility(GONE);
            mBio.setText(user.getBio());
            mBio.setVisibility(VISIBLE);
        } else {
            mBio.setVisibility(GONE);
            mNoDetails.setVisibility(VISIBLE);
        }
    }

}
