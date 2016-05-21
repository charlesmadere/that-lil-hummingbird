package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserActivity extends BaseDrawerActivity {

    private static final String TAG = "UserActivity";
    private static final String CNAME = UserActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_STARTING_POSITION = "StartingPosition";
    private static final String KEY_USER_DIGEST = "User";

    private int mStartingPosition;
    private String mUsername;
    private UserDigest mUserDigest;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final User user) {
        return getLaunchIntent(context, user.getId());
    }

    public static Intent getLaunchIntent(final Context context, final String username) {
        if (username.equalsIgnoreCase(CurrentUser.get().getId())) {
            return HomeActivity.getLaunchIntent(context);
        } else {
            return new Intent(context, UserActivity.class)
                    .putExtra(EXTRA_USERNAME, username);
        }
    }

    private void fetchUserDigest() {
        mSimpleProgressView.fadeIn();
        Api.getUserDigest(mUsername, new GetUserDigestListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setTitle(mUsername);

        mStartingPosition = UserFragmentAdapter.POSITION_FEED;

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mUserDigest = savedInstanceState.getParcelable(KEY_USER_DIGEST);
            mStartingPosition = savedInstanceState.getInt(KEY_STARTING_POSITION,
                    mStartingPosition);
        }

        if (mUserDigest == null) {
            fetchUserDigest();
        } else {
            showUserDigest(mUserDigest);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());

        if (mUserDigest != null) {
            outState.putParcelable(KEY_USER_DIGEST, mUserDigest);
        }
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_user_check_network_connection)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        fetchUserDigest();
                    }
                })
                .show();
    }

    private void showUserDigest(final UserDigest userDigest) {
        mUserDigest = userDigest;

        final User user = mUserDigest.getUser();
        PaletteUtils.applyParallaxColors(user.getCoverImageUrl(), this, mAppBarLayout,
                mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        mAvatar.setImageURI(Uri.parse(user.getAvatarLargest()));

        mViewPager.setAdapter(new UserFragmentAdapter(this, mUserDigest));
        mViewPager.setCurrentItem(mStartingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        mSimpleProgressView.fadeOut();
    }


    private static class GetUserDigestListener implements ApiResponse<UserDigest> {
        private final WeakReference<UserActivity> mActivityReference;

        private GetUserDigestListener(final UserActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final UserActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showUserDigest(userDigest);
            }
        }
    }

}
