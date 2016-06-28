package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseUserFragmentAdapter;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseFeedFragment;
import com.charlesmadere.hummingbird.fragments.UserFeedFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AvatarView;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class UserActivity extends BaseDrawerActivity implements BaseFeedFragment.Listener {

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

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton mPostToFeed;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.proBadge)
    TextView mProBadge;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final User user) {
        return getLaunchIntent(context, user.getId());
    }

    public static Intent getLaunchIntent(final Context context, final String username) {
        if (username.equalsIgnoreCase(CurrentUser.get().getUserId())) {
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFeedBeganLoading() {
        updatePostToFeedVisibility();
    }

    @Override
    public void onFeedFinishedLoading() {
        updatePostToFeedVisibility();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miFollow:
            case R.id.miUnfollow:
                toggleFollowingOfUser();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floatingActionButton)
    void onPostToFeedClick() {
        final UserFragmentAdapter adapter = (UserFragmentAdapter) mViewPager.getAdapter();
        final UserFeedFragment fragment = adapter.getFeedFragment();

        if (fragment != null) {
            fragment.showFeedPostFragment();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mUserDigest != null) {
            if (mUserDigest.getUser().isFollowed()) {
                menu.findItem(R.id.miUnfollow).setVisible(true);
            } else {
                menu.findItem(R.id.miFollow).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());

        if (mUserDigest != null) {
            outState.putParcelable(KEY_USER_DIGEST, mUserDigest);
        }
    }

    @OnPageChange(R.id.viewPager)
    void onViewPagerPageChange() {
        updatePostToFeedVisibility();
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

        if (user.hasCoverImage()) {
            PaletteUtils.applyParallaxColors(user.getCoverImage(), this, mAppBarLayout,
                    mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        }

        mAvatar.setContent(user);

        if (user.isPro()) {
            mProBadge.setVisibility(View.VISIBLE);
        }

        mViewPager.setAdapter(new UserFragmentAdapter(this, mUserDigest));
        mViewPager.setCurrentItem(mStartingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        updatePostToFeedVisibility();
        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }

    private void toggleFollowingOfUser() {
        mUserDigest.getUser().toggleFollowed();
        Api.toggleFollowingOfUser(mUsername);
        supportInvalidateOptionsMenu();
    }

    private void updatePostToFeedVisibility() {
        if (mViewPager.getCurrentItem() == UserFragmentAdapter.POSITION_FEED) {
            final BaseFeedFragment fragment = ((BaseUserFragmentAdapter) mViewPager.getAdapter())
                    .getFeedFragment();

            if (fragment == null || fragment.isFetchingFeed()) {
                mPostToFeed.hide();
            } else {
                mPostToFeed.show();
            }
        } else {
            mPostToFeed.hide();
        }
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
