package com.charlesmadere.hummingbird.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseLibraryFragment;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.preferences.Preferences;

import butterknife.BindView;

public abstract class BaseLibraryActivity extends BaseDrawerActivity implements
        BaseLibraryFragment.Listeners {

    private static final String CNAME = BaseLibraryActivity.class.getCanonicalName();
    protected static final String EXTRA_USERNAME = CNAME + ".Username";
    protected static final String KEY_LIBRARY_SORT = "LibrarySort";

    protected LibrarySort mLibrarySort;
    protected String mUsername;

    @BindView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    protected ViewPager mViewPager;


    @Override
    protected void applyUiColorSet(final UiColorSet uiColorSet) {
        super.applyUiColorSet(uiColorSet);
        mTabLayout.setBackgroundColor(uiColorSet.getDarkVibrantColor());
        mTabLayout.setSelectedTabIndicatorColor(uiColorSet.getVibrantColor());
    }

    protected abstract BaseLibraryFragmentAdapter getAdapter();

    @Override
    public LibrarySort getLibrarySort() {
        return mLibrarySort;
    }

    @Override
    public String getUsername() {
        return mUsername;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        final Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_USERNAME)) {
                mUsername = intent.getStringExtra(EXTRA_USERNAME);
                setSubtitle(mUsername);
            }
        }

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibrarySort = savedInstanceState.getParcelable(KEY_LIBRARY_SORT);
        }

        if (mLibrarySort == null) {
            mLibrarySort = Preferences.General.DefaultLibrarySort.get();
        }

        mViewPager.setAdapter(getAdapter());
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_library, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSortDate:
                setLibrarySort(LibrarySort.DATE);
                return true;

            case R.id.miSortRating:
                setLibrarySort(LibrarySort.RATING);
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
        menu.findItem(R.id.miSortRating).setEnabled(mLibrarySort != LibrarySort.RATING);
        menu.findItem(R.id.miSortTitle).setEnabled(mLibrarySort != LibrarySort.TITLE);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_LIBRARY_SORT, mLibrarySort);
    }

    protected void setLibrarySort(final LibrarySort librarySort) {
        mLibrarySort = librarySort;
        getAdapter().updateLibrarySort();
        supportInvalidateOptionsMenu();
    }

}
