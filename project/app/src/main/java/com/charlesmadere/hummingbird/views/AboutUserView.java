package com.charlesmadere.hummingbird.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.activities.FollowersActivity;
import com.charlesmadere.hummingbird.activities.FollowingActivity;
import com.charlesmadere.hummingbird.activities.UserAnimeReviewsActivity;
import com.charlesmadere.hummingbird.activities.UserGroupsActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUserView extends CardView implements AdapterView<UserDigest> {

    private NumberFormat mNumberFormat;
    private UserDigest mUserDigest;

    @BindView(R.id.hbivFollowers)
    HeadBodyItemView mFollowers;

    @BindView(R.id.hbivFollowing)
    HeadBodyItemView mFollowing;

    @BindView(R.id.hbivLocation)
    HeadBodyItemView mLocation;

    @BindView(R.id.hbivWaifuOrHusbando)
    HeadBodyItemView mWaifuOrHusbando;

    @BindView(R.id.hbivWebsite)
    HeadBodyItemView mWebsite;

    @BindView(R.id.tvAbout)
    TextView mAbout;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public AboutUserView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutUserView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.tvAnimeReviews)
    void onAnimeReviewsClick() {
        final Context context = getContext();
        context.startActivity(UserAnimeReviewsActivity.getLaunchIntent(context,
                mUserDigest.getUserId()));
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

    @OnClick(R.id.hbivFollowers)
    void onFollowersClick() {
        final Activity activity = MiscUtils.getActivity(getContext());
        final UiColorSet uiColorSet = activity instanceof PaletteUtils.Listener ?
                ((PaletteUtils.Listener) activity).getUiColorSet() : null;

        activity.startActivity(FollowersActivity.getLaunchIntent(activity, mUserDigest.getUserId(),
                uiColorSet));
    }

    @OnClick(R.id.hbivFollowing)
    void onFollowingClick() {
        final Activity activity = MiscUtils.getActivity(getContext());
        final UiColorSet uiColorSet = activity instanceof PaletteUtils.Listener ?
                ((PaletteUtils.Listener) activity).getUiColorSet() : null;

        activity.startActivity(FollowingActivity.getLaunchIntent(activity,
                mUserDigest.getUserId(), uiColorSet));
    }

    @OnClick(R.id.tvGroups)
    void onGroupsClick() {
        final Activity activity = MiscUtils.getActivity(getContext());
        final UiColorSet uiColorSet = activity instanceof PaletteUtils.Listener ?
                ((PaletteUtils.Listener) activity).getUiColorSet() : null;

        activity.startActivity(UserGroupsActivity.getLaunchIntent(activity,
                mUserDigest.getUserId(), uiColorSet));
    }

    @OnClick(R.id.hbivWaifuOrHusbando)
    void onWaifuOrHusbandoClick() {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context,
                mUserDigest.getUser().getWaifuSlug()));
    }

    @OnClick(R.id.hbivWebsite)
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
            mWaifuOrHusbando.setHead(user.getWaifu());
            mWaifuOrHusbando.setBody(user.getWaifuOrHusbando().getTextResId());
            mWaifuOrHusbando.setVisibility(VISIBLE);
        } else {
            mWaifuOrHusbando.setVisibility(GONE);
        }

        if (user.hasLocation()) {
            mLocation.setHead(user.getLocation());
            mLocation.setVisibility(VISIBLE);
        } else {
            mLocation.setVisibility(GONE);
        }

        if (user.hasWebsite()) {
            mWebsite.setHead(user.getWebsite());
            mWebsite.setVisibility(VISIBLE);
        } else {
            mWebsite.setVisibility(GONE);
        }

        mFollowers.setHead(mNumberFormat.format(user.getFollowerCount()));
        mFollowers.setBody(res.getQuantityText(R.plurals.followers, user.getFollowerCount()));

        mFollowing.setHead(mNumberFormat.format(user.getFollowingCount()));
    }

}
