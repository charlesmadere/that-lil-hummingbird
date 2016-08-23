package com.charlesmadere.hummingbird.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseUserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseUserFeedFragment;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.misc.FeedListeners;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.ApiResponse;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public abstract class BaseUserActivity extends BaseDrawerActivity implements
        BaseUserFeedFragment.Listener, FeedListeners, FeedPostFragment.Listener {

    @BindView(R.id.floatingActionButton)
    protected FloatingActionButton mPostToFeed;

    @BindView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    protected ViewPager mViewPager;


    private void feedPostFailure() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.error_posting_to_feed)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void fetchFeed() {
        final BaseUserFragmentAdapter adapter = (BaseUserFragmentAdapter) mViewPager.getAdapter();
        final BaseUserFeedFragment fragment = adapter.getFeedFragment();

        if (fragment != null) {
            fragment.fetchFeed();
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

    @OnClick(R.id.floatingActionButton)
    protected void onPostToFeedClick() {
        FeedPostFragment.create().show(getSupportFragmentManager(), FeedPostFragment.TAG);
    }

    @OnPageChange(R.id.viewPager)
    protected void onViewPagerPageChange() {
        updatePostToFeedVisibility();
    }

    protected void setAdapter(final BaseUserFragmentAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        updatePostToFeedVisibility();
    }

    protected void updatePostToFeedVisibility() {
        if (mViewPager.getCurrentItem() == BaseUserFragmentAdapter.POSITION_FEED) {
            final BaseUserFeedFragment fragment = ((BaseUserFragmentAdapter) mViewPager.getAdapter())
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


    protected static class FeedPostListener implements ApiResponse<Void> {
        private final WeakReference<BaseUserActivity> mActivityReference;

        protected FeedPostListener(final BaseUserActivity activity) {
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
