package com.charlesmadere.hummingbird.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseUserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseUserFeedFragment;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public abstract class BaseUserActivity extends BaseDrawerActivity implements
        BaseUserFeedFragment.Listener, FeedPostFragment.Listener {

    protected static final String KEY_STARTING_POSITION = "StartingPosition";

    protected int mStartingPosition;

    @BindView(R.id.floatingActionButton)
    protected FloatingActionButton mPostToFeed;

    @BindView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    protected ViewPager mViewPager;


    @LayoutRes
    protected abstract int getContentView();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mStartingPosition = BaseUserFragmentAdapter.POSITION_FEED;

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mStartingPosition = savedInstanceState.getInt(KEY_STARTING_POSITION, mStartingPosition);
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

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());
    }

    @OnPageChange(R.id.viewPager)
    protected void onViewPagerPageChange() {
        updatePostToFeedVisibility();
    }

    protected void setAdapter(final BaseUserFragmentAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mStartingPosition);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
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

}
