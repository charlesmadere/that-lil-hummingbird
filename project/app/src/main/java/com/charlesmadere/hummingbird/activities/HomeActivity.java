package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseUserFragmentAdapter;
import com.charlesmadere.hummingbird.adapters.HomeFragmentAdapter;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseFeedFragment;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.fragments.LibraryUpdateFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.SyncManager;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class HomeActivity extends BaseDrawerActivity implements BaseFeedFragment.Listener,
        FeedPostFragment.Listener, LibraryUpdateFragment.Listener {

    private static final String TAG = "HomeActivity";
    private static final String KEY_STARTING_POSITION = "StartingPosition";

    @BindView(R.id.floatingActionButton)
    FloatingActionButton mPostToFeed;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    static {
        SyncManager.enableOrDisable();
    }

    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public static Intent getNewTaskLaunchIntent(final Context context) {
        return getLaunchIntent(context).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.HOME;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int startingPosition = UserFragmentAdapter.POSITION_FEED;

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            startingPosition = savedInstanceState.getInt(KEY_STARTING_POSITION, startingPosition);
        }

        mViewPager.setAdapter(new HomeFragmentAdapter(this));
        mViewPager.setCurrentItem(startingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        updatePostToFeedVisibility();
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
        final FeedPostFragment postFragment = (FeedPostFragment) getSupportFragmentManager()
                .findFragmentByTag(FeedPostFragment.TAG);
        final FeedPost feedPost = postFragment.getFeedPost(CurrentUser.get().getUserId());

        if (feedPost == null) {
            return;
        }

        final BaseUserFragmentAdapter adapter = (BaseUserFragmentAdapter) mViewPager.getAdapter();
        final BaseFeedFragment feedFragment = adapter.getFeedFragment();
        feedFragment.postToFeed(feedPost);
    }

    @Override
    public void onLibraryUpdateSave() {
        // TODO
    }

    @OnClick(R.id.floatingActionButton)
    void onPostToFeedClick() {
        FeedPostFragment.create().show(getSupportFragmentManager(), FeedPostFragment.TAG);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());
    }

    @OnPageChange(R.id.viewPager)
    void onViewPagerPageChange() {
        updatePostToFeedVisibility();
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

}
