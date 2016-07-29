package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.MangaLibraryFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.preferences.Preferences;

import butterknife.BindView;

public class MangaLibraryActivity extends BaseDrawerActivity implements
        MangaLibraryFragment.Listener {

    private static final String TAG = "MangaLibraryActivity";
    private static final String CNAME = MangaLibraryActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_LIBRARY_SORT = "LibrarySort";

    private LibrarySort mLibrarySort;
    private MangaLibraryFragmentAdapter mAdapter;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return new Intent(context, MangaLibraryActivity.class)
                .putExtra(EXTRA_USERNAME, username);
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
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_library);

        final Intent intent = getIntent();
        final String username = intent.getStringExtra(EXTRA_USERNAME);
        setSubtitle(username);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibrarySort = savedInstanceState.getParcelable(KEY_LIBRARY_SORT);
        }

        if (mLibrarySort == null) {
            mLibrarySort = Preferences.General.DefaultLibrarySort.get();
        }

        mAdapter = new MangaLibraryFragmentAdapter(this, username,
                CurrentUser.get().getUserId().equalsIgnoreCase(username));
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_manga_library, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSortDate:
                setLibrarySort(LibrarySort.DATE);
                return true;

            case R.id.miSortTitle:
                setLibrarySort(LibrarySort.TITLE);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
        outState.putParcelable(KEY_LIBRARY_SORT, mLibrarySort);
    }

    private void setLibrarySort(final LibrarySort librarySort) {
        mLibrarySort = librarySort;
        mAdapter.updateLibrarySort();
        supportInvalidateOptionsMenu();
    }

}
