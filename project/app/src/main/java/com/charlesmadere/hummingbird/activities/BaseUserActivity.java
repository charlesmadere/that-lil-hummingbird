package com.charlesmadere.hummingbird.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseUserFragment;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.fragments.UserFeedFragment;
import com.charlesmadere.hummingbird.misc.FeedListeners;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AvatarView;
import com.charlesmadere.hummingbird.views.ParallaxCoverImage;
import com.charlesmadere.hummingbird.views.SimpleProgressView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public abstract class BaseUserActivity extends BaseDrawerActivity implements
        BaseUserFragment.Listeners, FeedListeners, FeedPostFragment.Listener,
        PaletteUtils.Listener {

    private static final String CNAME = BaseUserActivity.class.getCanonicalName();
    protected static final String EXTRA_INITIAL_TAB = CNAME + ".InitialTab";
    private static final String KEY_INITIAL_TAB = "InitialTab";

    public static final int TAB_FEED = UserFragmentAdapter.POSITION_FEED;
    public static final int TAB_PROFILE = UserFragmentAdapter.POSITION_PROFILE;
    public static final int TAB_GROUPS = UserFragmentAdapter.POSITION_GROUPS;

    protected int mInitialTab;
    protected UiColorSet mUiColorSet;

    @BindView(R.id.appBarLayout)
    protected AppBarLayout mAppBarLayout;

    @BindView(R.id.avatarView)
    protected AvatarView mAvatar;

    @BindView(R.id.collapsingToolbarLayout)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.floatingActionButton)
    protected FloatingActionButton mPostToFeed;

    @BindView(R.id.parallaxCoverImage)
    protected ParallaxCoverImage mCoverImage;

    @BindView(R.id.simpleProgressView)
    protected SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    @BindView(R.id.proBadge)
    protected View mProBadge;

    @BindView(R.id.viewPager)
    protected ViewPager mViewPager;


    private void feedPostFailure() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.error_posting_to_feed)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void fetchFeed() {
        final UserFragmentAdapter adapter = (UserFragmentAdapter) mViewPager.getAdapter();
        final UserFeedFragment fragment = adapter.getFeedFragment();

        if (fragment != null) {
            fragment.fetchFeed();
        }
    }

    @Nullable
    @Override
    public UiColorSet getUiColorSet() {
        return mUiColorSet;
    }

    public abstract String getUsername();

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_user);

        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_INITIAL_TAB)) {
            mInitialTab = intent.getIntExtra(EXTRA_INITIAL_TAB, 0);
        }

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mInitialTab = savedInstanceState.getInt(KEY_INITIAL_TAB, 0);
        }
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
    public void onFeedPostSubmit() {
        final FeedPostFragment fragment = (FeedPostFragment) getSupportFragmentManager()
                .findFragmentByTag(FeedPostFragment.TAG);
        final FeedPost post = fragment.getFeedPost(getUsername());

        if (post == null) {
            return;
        }

        Api.postToFeed(post, new FeedPostListener(this));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAnimeLibrary:
                startActivity(AnimeLibraryActivity.getLaunchIntent(this, getUsername(), mUiColorSet));
                return true;

            case R.id.miMangaLibrary:
                startActivity(MangaLibraryActivity.getLaunchIntent(this, getUsername(), mUiColorSet));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floatingActionButton)
    protected void onPostToFeedClick() {
        FeedPostFragment.create().show(getSupportFragmentManager(), FeedPostFragment.TAG);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INITIAL_TAB, mViewPager.getCurrentItem());
    }

    @Override
    public void onUiColorsBuilt(final UiColorSet uiColorSet) {
        mUiColorSet = uiColorSet;
    }

    @OnPageChange(R.id.viewPager)
    protected void onViewPagerPageChange() {
        updatePostToFeedVisibility();
    }

    protected void setAdapter(final UserFragmentAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(mInitialTab);
        updatePostToFeedVisibility();
    }

    @Override
    public void setUserDigest(final UserDigest userDigest) {
        if (TextUtils.isEmpty(getTitle())) {
            setTitle(userDigest.getUserId());
        }

        final User user = userDigest.getUser();

        if (user.hasCoverImage()) {
            PaletteUtils.applyParallaxColors(user.getCoverImage(), this, this, mCoverImage,
                    mAppBarLayout, mCollapsingToolbarLayout, mTabLayout);
        }

        mAvatar.setContent(user);

        if (user.isPro()) {
            mProBadge.setVisibility(View.VISIBLE);
        }

        final PagerAdapter adapter = mViewPager.getAdapter();

        if (adapter == null) {
            setAdapter(new UserFragmentAdapter(this));
        }

        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }

    protected void updatePostToFeedVisibility() {
        if (mViewPager.getCurrentItem() == UserFragmentAdapter.POSITION_FEED) {
            final UserFeedFragment fragment = ((UserFragmentAdapter) mViewPager.getAdapter())
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


    private static class FeedPostListener implements ApiResponse<Void> {
        private final WeakReference<BaseUserActivity> mActivityReference;

        private FeedPostListener(final BaseUserActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseUserActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.feedPostFailure();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final BaseUserActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.fetchFeed();
            }
        }
    }

}
