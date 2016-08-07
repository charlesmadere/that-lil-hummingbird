package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.AnimeEpisodeFragment;
import com.charlesmadere.hummingbird.fragments.AnimeLibraryUpdateFragment;
import com.charlesmadere.hummingbird.fragments.BaseAnimeFragment;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.misc.ShareUtils;
import com.charlesmadere.hummingbird.models.AddAnimeLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.Anime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AnimeEpisodeItemView;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class AnimeActivity extends BaseDrawerActivity implements
        AnimeEpisodeItemView.OnClickListener, AnimeLibraryUpdateFragment.UpdateListener,
        BaseAnimeFragment.Listener, ObjectCache.KeyProvider, PaletteUtils.Listener {

    private static final String TAG = "AnimeActivity";
    private static final String CNAME = AnimeActivity.class.getCanonicalName();
    private static final String EXTRA_ANIME_ID = CNAME + ".AnimeId";
    private static final String EXTRA_ANIME_NAME = CNAME + ".AnimeName";

    private AnimeDigest mAnimeDigest;
    private String mAnimeId;
    private UiColorSet mUiColorSet;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final Anime anime) {
        return getLaunchIntent(context, anime.getId(), anime.getTitle());
    }

    public static Intent getLaunchIntent(final Context context, final String animeId) {
        return getLaunchIntent(context, animeId, null);
    }

    public static Intent getLaunchIntent(final Context context, final String animeId,
            @Nullable final String animeName) {
        final Intent intent = new Intent(context, AnimeActivity.class)
                .putExtra(EXTRA_ANIME_ID, animeId);

        if (!TextUtils.isEmpty(animeName)) {
            intent.putExtra(EXTRA_ANIME_NAME, animeName);
        }

        return intent;
    }

    private void addedLibraryEntry(final AddAnimeLibraryEntryResponse response) {
        mAnimeDigest.getInfo().setLibraryEntryId(response.getLibraryEntry().getId());
        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
        Toast.makeText(this, R.string.added_to_library, Toast.LENGTH_LONG).show();
    }

    private void fetchAnimeDigest() {
        mSimpleProgressView.fadeIn();
        Api.getAnimeDigest(mAnimeId, new GetAnimeDigestListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public AnimeDigest getAnimeDigest() {
        return mAnimeDigest;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mAnimeId };
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
    public void onClick(final AnimeEpisodeItemView v) {
        AnimeEpisodeFragment.create(v.getEpisode()).show(getSupportFragmentManager(), null);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        final Intent intent = getIntent();
        mAnimeId = intent.getStringExtra(EXTRA_ANIME_ID);

        if (intent.hasExtra(EXTRA_ANIME_NAME)) {
            setTitle(intent.getStringExtra(EXTRA_ANIME_NAME));
        }

        mAnimeDigest = ObjectCache.get(this);

        if (mAnimeDigest == null) {
            fetchAnimeDigest();
        } else {
            showAnimeDigest(mAnimeDigest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_anime, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddToLibrary:
                AnimeLibraryUpdateFragment.create(mAnimeDigest).show(getSupportFragmentManager(),
                        AnimeLibraryUpdateFragment.TAG);
                return true;

            case R.id.miShare:
                ShareUtils.shareAnime(this, mAnimeDigest);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mAnimeDigest != null) {
            menu.findItem(R.id.miShare).setVisible(true);

            if (!mAnimeDigest.getInfo().hasLibraryEntryId()) {
                menu.findItem(R.id.miAddToLibrary).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAnimeDigest != null) {
            ObjectCache.put(mAnimeDigest, this);
        }
    }

    @Override
    public void onUiColorsBuilt(final UiColorSet uiColorSet) {
        mUiColorSet = uiColorSet;
    }

    @Override
    public void onUpdateLibraryEntry() {
        final AnimeLibraryUpdateFragment fragment = (AnimeLibraryUpdateFragment)
                getSupportFragmentManager().findFragmentByTag(AnimeLibraryUpdateFragment.TAG);
        final AnimeLibraryUpdate libraryUpdate = fragment.getLibraryUpdate();

        mSimpleProgressView.fadeIn();
        Api.addAnimeLibraryEntry(libraryUpdate, new AddLibraryEntryListener(this));
    }

    private void showAddLibraryEntryError() {
        mSimpleProgressView.fadeOut();
        Toast.makeText(this, R.string.error_adding_library_entry, Toast.LENGTH_LONG).show();
    }

    private void showAnimeDigest(final AnimeDigest animeDigest) {
        mAnimeDigest = animeDigest;

        if (TextUtils.isEmpty(getTitle())) {
            setTitle(mAnimeDigest.getTitle());
        }

        if (animeDigest.getInfo().hasCoverImage()) {
            PaletteUtils.applyParallaxColors(animeDigest.getInfo().getCoverImage(), this,
                    this, mCoverImage, mAppBarLayout, mCollapsingToolbarLayout, mTabLayout);
        }

        mViewPager.setAdapter(new AnimeFragmentAdapter(this, mAnimeDigest));
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_anime)
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
                        fetchAnimeDigest();
                    }
                })
                .show();
    }


    private static class AddLibraryEntryListener implements ApiResponse<AddAnimeLibraryEntryResponse> {
        private final WeakReference<AnimeActivity> mActivityReference;

        private AddLibraryEntryListener(final AnimeActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showAddLibraryEntryError();
            }
        }

        @Override
        public void success(final AddAnimeLibraryEntryResponse response) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.addedLibraryEntry(response);
            }
        }
    }

    private static class GetAnimeDigestListener implements ApiResponse<AnimeDigest> {
        private final WeakReference<AnimeActivity> mActivityReference;

        private GetAnimeDigestListener(final AnimeActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final AnimeDigest animeDigest) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showAnimeDigest(animeDigest);
            }
        }
    }

}
