package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseUserFragmentAdapter;
import com.charlesmadere.hummingbird.adapters.HomeFragmentAdapter;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.AnimeLibraryFragment;
import com.charlesmadere.hummingbird.fragments.BaseFeedFragment;
import com.charlesmadere.hummingbird.fragments.HomeFeedFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.SyncManager;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class HomeActivity extends BaseDrawerActivity implements AnimeLibraryFragment.Listener,
        BaseFeedFragment.Listener {

    private static final String TAG = "HomeActivity";
    private static final String KEY_LIBRARY_SORT = "LibrarySort";
    private static final String KEY_STARTING_POSITION = "StartingPosition";

    private HomeFragmentAdapter mAdapter;
    private LibrarySort mLibrarySort;

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
    public LibrarySort getLibrarySort() {
        return mLibrarySort;
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
            mLibrarySort = savedInstanceState.getParcelable(KEY_LIBRARY_SORT);
        }

        if (mLibrarySort == null) {
            mLibrarySort = Preferences.General.DefaultLibrarySort.get();
        }

        mAdapter = new HomeFragmentAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(startingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        updatePostToFeedVisibility();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
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
            case R.id.miMangaLibrary:
                startActivity(MangaLibraryActivity.getLaunchIntent(this, CurrentUser.get().getUserId()));
                return true;

            case R.id.miSortDate:
                setLibrarySort(LibrarySort.DATE);
                return true;

            case R.id.miSortTitle:
                setLibrarySort(LibrarySort.TITLE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floatingActionButton)
    void onPostToFeedClick() {
        final HomeFragmentAdapter adapter = (HomeFragmentAdapter) mViewPager.getAdapter();
        final HomeFeedFragment fragment = adapter.getFeedFragment();

        if (fragment != null) {
            fragment.showFeedPostFragment();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.miSortDate).setEnabled(mLibrarySort != LibrarySort.DATE);
        menu.findItem(R.id.miSortTitle).setEnabled(mLibrarySort != LibrarySort.TITLE);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());
        outState.putParcelable(KEY_LIBRARY_SORT, mLibrarySort);
    }

    @OnPageChange(R.id.viewPager)
    void onViewPagerPageChange() {
        updatePostToFeedVisibility();
    }

    private void setLibrarySort(final LibrarySort librarySort) {
        mLibrarySort = librarySort;

        if (mAdapter != null) {
            mAdapter.updateLibrarySort();
        }

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

}
