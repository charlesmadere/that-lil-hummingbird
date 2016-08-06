package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AvatarView;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserActivity extends BaseUserActivity implements ObjectCache.KeyProvider {

    private static final String TAG = "UserActivity";
    private static final String CNAME = UserActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_UI_COLOR_SET = "UiColorSet";

    private String mUsername;
    private UiColorSet mUiColorSet;
    private UserDigest mUserDigest;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.proBadge)
    View mProBadge;


    public static Intent getLaunchIntent(final Context context, final User user) {
        return getLaunchIntent(context, user.getId());
    }

    public static Intent getLaunchIntent(final Context context, final String username) {
        if (username.equalsIgnoreCase(CurrentUser.get().getUserId())) {
            return FeedActivity.getLaunchIntent(context);
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
    protected int getContentView() {
        return R.layout.activity_user;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mUsername };
    }

    @Override
    public UserDigest getUserDigest() {
        return mUserDigest;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setTitle(mUsername);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mUiColorSet = savedInstanceState.getParcelable(KEY_UI_COLOR_SET);
        }

        mUserDigest = ObjectCache.get(this);

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
    public void onFeedPostSubmit() {
        final FeedPostFragment fragment = (FeedPostFragment) getSupportFragmentManager()
                .findFragmentByTag(FeedPostFragment.TAG);
        final FeedPost post = fragment.getFeedPost(mUsername);

        if (post == null) {
            return;
        }

        Api.postToFeed(post, new FeedPostListener(this));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miFollow:
            case R.id.miUnfollow:
                toggleFollowingOfUser();
                return true;

            case R.id.miAnimeLibrary:
                startActivity(AnimeLibraryActivity.getLaunchIntent(this, mUsername));
                return true;

            case R.id.miMangaLibrary:
                startActivity(MangaLibraryActivity.getLaunchIntent(this, mUsername));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mUserDigest != null) {
            menu.findItem(R.id.miAnimeLibrary).setVisible(true);
            menu.findItem(R.id.miMangaLibrary).setVisible(true);

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

        if (mUiColorSet != null) {
            outState.putParcelable(KEY_UI_COLOR_SET, mUiColorSet);
        }

        if (mUserDigest != null) {
            ObjectCache.put(mUserDigest, this);
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

        if (user.hasCoverImage()) {
            PaletteUtils.applyParallaxColors(user.getCoverImage(), this, mAppBarLayout,
                    mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        }

        mAvatar.setContent(user);

        if (user.isPro()) {
            mProBadge.setVisibility(View.VISIBLE);
        }

        setAdapter(new UserFragmentAdapter(this, mUsername));
        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }

    private void toggleFollowingOfUser() {
        mUserDigest.getUser().toggleFollowed();
        Api.toggleFollowingOfUser(mUsername);
        supportInvalidateOptionsMenu();
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
