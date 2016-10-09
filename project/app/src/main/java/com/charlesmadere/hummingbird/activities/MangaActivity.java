package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseMangaFragment;
import com.charlesmadere.hummingbird.fragments.MangaLibraryUpdateFragment;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.misc.ShareUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Manga;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.ParallaxCoverImage;
import com.charlesmadere.hummingbird.views.SimpleProgressView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class MangaActivity extends BaseDrawerActivity implements BaseMangaFragment.Listener,
        MangaLibraryUpdateFragment.DigestListener, MangaLibraryUpdateFragment.LibraryEntryListener,
        ObjectCache.KeyProvider, PaletteUtils.Listener {

    private static final String TAG = "MangaActivity";
    private static final String CNAME = MangaActivity.class.getCanonicalName();
    private static final String EXTRA_MANGA_ID = CNAME + ".MangaId";
    private static final String EXTRA_MANGA_NAME = CNAME + ".MangaName";
    private static final String ADD_TAG = MangaLibraryUpdateFragment.TAG + "|Add";
    private static final String EDIT_TAG = MangaLibraryUpdateFragment.TAG + "|Edit";

    private MangaDigest mMangaDigest;
    private MangaFragmentAdapter mAdapter;
    private String mMangaId;
    private UiColorSet mUiColorSet;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.parallaxCoverImage)
    ParallaxCoverImage mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final Manga manga) {
        return getLaunchIntent(context, manga.getId(), manga.getTitle());
    }

    public static Intent getLaunchIntent(final Context context, final String mangaId) {
        return getLaunchIntent(context, mangaId, null);
    }

    public static Intent getLaunchIntent(final Context context, final String mangaId,
            @Nullable final String mangaName) {
        final Intent intent = new Intent(context, MangaActivity.class)
                .putExtra(EXTRA_MANGA_ID, mangaId);

        if (!TextUtils.isEmpty(mangaName)) {
            intent.putExtra(EXTRA_MANGA_NAME, mangaName);
        }

        return intent;
    }

    private void addedLibraryEntry(final MangaLibraryEntryResponse response) {
        mMangaDigest.addLibraryEntry(response.getLibraryEntry());
        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
        Toast.makeText(this, R.string.added_to_library, Toast.LENGTH_LONG).show();
    }

    private void editedLibraryEntry(final MangaLibraryEntryResponse response) {
        mMangaDigest.addLibraryEntry(response.getLibraryEntry());
        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
        Toast.makeText(this, R.string.saved_changes, Toast.LENGTH_LONG).show();
    }

    private void fetchMangaDigest() {
        mSimpleProgressView.fadeIn();
        Api.getMangaDigest(mMangaId, new GetMangaDigestListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public MangaDigest getMangaDigest() {
        return mMangaDigest;
    }

    @Override
    public MangaLibraryEntry getMangaLibraryEntry(final String libraryEntryId) {
        return mMangaDigest.getLibraryEntry();
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mMangaId };
    }

    @Nullable
    @Override
    public UiColorSet getUiColorSet() {
        return mUiColorSet;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        final Intent intent = getIntent();
        mMangaId = intent.getStringExtra(EXTRA_MANGA_ID);

        if (intent.hasExtra(EXTRA_MANGA_NAME)) {
            setTitle(intent.getStringExtra(EXTRA_MANGA_NAME));
        }

        mMangaDigest = ObjectCache.get(this);

        if (mMangaDigest == null) {
            fetchMangaDigest();
        } else {
            showMangaDigest(mMangaDigest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_manga, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddToLibrary:
                MangaLibraryUpdateFragment.create().show(getSupportFragmentManager(), ADD_TAG);
                return true;

            case R.id.miEditInLibrary:
                MangaLibraryUpdateFragment.create(mMangaDigest.getLibraryEntry().getId())
                        .show(getSupportFragmentManager(), EDIT_TAG);
                return true;

            case R.id.miShare:
                ShareUtils.shareManga(this, mMangaDigest);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mMangaDigest != null) {
            menu.findItem(R.id.miShare).setVisible(true);

            if (mMangaDigest.hasLibraryEntries()) {
                menu.findItem(R.id.miEditInLibrary).setVisible(true);
            } else {
                menu.findItem(R.id.miAddToLibrary).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mMangaDigest != null) {
            ObjectCache.put(mMangaDigest, this);
        }
    }

    @Override
    public void onUiColorsBuilt(final UiColorSet uiColorSet) {
        mUiColorSet = uiColorSet;
    }

    @Override
    public void onUpdateLibraryEntry() {
        mSimpleProgressView.fadeIn();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        MangaLibraryUpdateFragment fragment = (MangaLibraryUpdateFragment)
                fragmentManager.findFragmentByTag(ADD_TAG);

        if (fragment == null) {
            fragment = (MangaLibraryUpdateFragment) fragmentManager.findFragmentByTag(EDIT_TAG);
            final String libraryEntryId = fragment.getLibraryEntryId();
            final MangaLibraryUpdate libraryUpdate = fragment.getLibraryUpdate();
            Api.updateMangaLibraryEntry(libraryEntryId, libraryUpdate, new EditLibraryEntryListener(this));
        } else {
            final MangaLibraryUpdate libraryUpdate = fragment.getLibraryUpdate();
            Api.addMangaLibraryEntry(libraryUpdate, new AddLibraryEntryListener(this));
        }
    }

    private void showAddLibraryEntryError() {
        mSimpleProgressView.fadeOut();
        Toast.makeText(this, R.string.error_adding_library_entry, Toast.LENGTH_LONG).show();
    }

    private void showEditLibraryEntryError() {
        mSimpleProgressView.fadeOut();
        Toast.makeText(this, R.string.error_editing_library_entry, Toast.LENGTH_LONG).show();
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_manga)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        fetchMangaDigest();
                    }
                })
                .show();
    }

    private void showMangaDigest(final MangaDigest mangaDigest) {
        mMangaDigest = mangaDigest;

        if (TextUtils.isEmpty(getTitle())) {
            setTitle(mMangaDigest.getTitle());
        }

        if (mangaDigest.getInfo().hasCoverImage()) {
            PaletteUtils.applyParallaxColors(mangaDigest.getInfo().getCoverImage(), this, this,
                    mCoverImage, mAppBarLayout, mCollapsingToolbarLayout, mTabLayout);
        }

        if (mAdapter == null) {
            mAdapter = new MangaFragmentAdapter(this, mMangaDigest);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
            mViewPager.setOffscreenPageLimit(3);
            mTabLayout.setupWithViewPager(mViewPager);
        } else {
            mAdapter.showMangaDigest();
        }

        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }


    private static class AddLibraryEntryListener implements ApiResponse<MangaLibraryEntryResponse> {
        private final WeakReference<MangaActivity> mActivityReference;

        private AddLibraryEntryListener(final MangaActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showAddLibraryEntryError();
            }
        }

        @Override
        public void success(final MangaLibraryEntryResponse response) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.addedLibraryEntry(response);
            }
        }
    }

    private static class EditLibraryEntryListener implements ApiResponse<MangaLibraryEntryResponse> {
        private final WeakReference<MangaActivity> mActivityReference;

        private EditLibraryEntryListener(final MangaActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showEditLibraryEntryError();
            }
        }

        @Override
        public void success(final MangaLibraryEntryResponse response) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.editedLibraryEntry(response);
            }
        }
    }

    private static class GetMangaDigestListener implements ApiResponse<MangaDigest> {
        private final WeakReference<MangaActivity> mActivityReference;

        private GetMangaDigestListener(final MangaActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(final MangaDigest mangaDigest) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showMangaDigest(mangaDigest);
            }
        }
    }

}
