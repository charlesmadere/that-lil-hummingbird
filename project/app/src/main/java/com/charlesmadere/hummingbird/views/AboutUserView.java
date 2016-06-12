package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.FollowersActivity;
import com.charlesmadere.hummingbird.activities.FollowingActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUserView extends CardView implements AdapterView<UserDigest> {

    private NumberFormat mNumberFormat;
    private UserDigest mUserDigest;

    @BindView(R.id.llLocation)
    LinearLayout mLocationContainer;

    @BindView(R.id.llWaifuOrHusbando)
    LinearLayout mWaifuOrHusbandoContainer;

    @BindView(R.id.llWebsite)
    LinearLayout mWebsiteContainer;

    @BindView(R.id.tvAbout)
    TextView mAbout;

    @BindView(R.id.tvFollowersBody)
    TextView mFollowersBody;

    @BindView(R.id.tvFollowersHeader)
    TextView mFollowersHeader;

    @BindView(R.id.tvFollowingHeader)
    TextView mFollowingHeader;

    @BindView(R.id.tvLocationHeader)
    TextView mLocationHeader;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.tvWaifuOrHusbandoBody)
    TextView mWaifuOrHusbandoBody;

    @BindView(R.id.tvWaifuOrHusbandoHeader)
    TextView mWaifuOrHusbandoHeader;

    @BindView(R.id.tvWebsiteHeader)
    TextView mWebsiteHeader;


    public AboutUserView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutUserView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @OnClick(R.id.llFollowers)
    void onFollowersClick() {
        final Context context = getContext();
        context.startActivity(FollowersActivity.getLaunchIntent(context, mUserDigest.getUserId()));
    }

    @OnClick(R.id.llFollowing)
    void onFollowingClick() {
        final Context context = getContext();
        context.startActivity(FollowingActivity.getLaunchIntent(context, mUserDigest.getUserId()));
    }

    @OnClick(R.id.llWaifuOrHusbando)
    void onWaifuOrHusbandoClick() {
        // TODO
    }

    @OnClick(R.id.llWebsite)
    void onWebsiteClick() {
        MiscUtils.openUrl(getContext(), mUserDigest.getUser().getWebsite());
    }

    @Override
    public void setContent(final UserDigest content) {
        mUserDigest = content;

        final User user = mUserDigest.getUser();
        final Resources res = getResources();
        mTitle.setText(res.getString(R.string.about_x, user.getId()));

        if (user.hasAbout()) {
            mAbout.setText(user.getAbout());
            mAbout.setVisibility(VISIBLE);
        } else {
            mAbout.setVisibility(GONE);
        }

        if (user.hasWaifuOrHusbando()) {
            mWaifuOrHusbandoHeader.setText(user.getWaifu());
            mWaifuOrHusbandoBody.setText(user.getWaifuOrHusbando().getTextResId());
            mWaifuOrHusbandoContainer.setVisibility(VISIBLE);
        } else {
            mWaifuOrHusbandoContainer.setVisibility(GONE);
        }

        if (user.hasLocation()) {
            mLocationHeader.setText(user.getLocation());
            mLocationContainer.setVisibility(VISIBLE);
        } else {
            mLocationContainer.setVisibility(GONE);
        }

        if (user.hasWebsite()) {
            mWebsiteHeader.setText(user.getWebsite());
            mWebsiteContainer.setVisibility(VISIBLE);
        } else {
            mWebsiteContainer.setVisibility(GONE);
        }

        mFollowersHeader.setText(mNumberFormat.format(user.getFollowerCount()));
        mFollowersBody.setText(res.getQuantityText(R.plurals.followers, user.getFollowerCount()));

        mFollowingHeader.setText(mNumberFormat.format(user.getFollowingCount()));
    }

}
