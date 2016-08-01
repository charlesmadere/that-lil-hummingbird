package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeReviewsAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class AnimeReviewsActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AnimeReviewsActivity";
    private static final String CNAME = AnimeReviewsActivity.class.getCanonicalName();
    private static final String EXTRA_ANIME_ID = CNAME + ".AnimeId";
    private static final String EXTRA_ANIME_TITLE = CNAME + ".AnimeTitle";

    private AnimeDigest mAnimeDigest;
    private String mAnimeId;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String animeId) {
        return getLaunchIntent(context, animeId, null);
    }

    public static Intent getLaunchIntent(final Context context, final String animeId,
            @Nullable final String animeTitle) {
        final Intent intent = new Intent(context, AnimeReviewsActivity.class)
                .putExtra(EXTRA_ANIME_ID, animeId);

        if (!TextUtils.isEmpty(animeTitle)) {
            intent.putExtra(EXTRA_ANIME_TITLE, animeTitle);
        }

        return intent;
    }

    private void fetchAnimeDigest() {
        mRefreshLayout.setRefreshing(true);
        Api.getAnimeDigest(mAnimeId, new GetAnimeDigestListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mAnimeId };
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_reviews);

        final Intent intent = getIntent();
        mAnimeId = intent.getStringExtra(EXTRA_ANIME_ID);

        if (intent.hasExtra(EXTRA_ANIME_TITLE)) {
            setSubtitle(intent.getStringExtra(EXTRA_ANIME_TITLE));
        }

        mAnimeDigest = ObjectCache.get(this);

        if (mAnimeDigest == null) {
            fetchAnimeDigest();
        } else if (mAnimeDigest.hasReviews()) {
            showReviews(mAnimeDigest);
        } else {
            showEmpty(mAnimeDigest);
        }
    }

    @Override
    public void onRefresh() {
        fetchAnimeDigest();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAnimeDigest != null) {
            ObjectCache.put(mAnimeDigest, this);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
    }

    private void showEmpty(final AnimeDigest animeDigest) {
        mAnimeDigest = animeDigest;
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showError() {
        mEmpty.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showReviews(final AnimeDigest animeDigest) {
        mAnimeDigest = animeDigest;

        if (TextUtils.isEmpty(getSubtitle())) {
            setSubtitle(mAnimeDigest.getTitle());
        }

        final AnimeReviewsAdapter adapter = new AnimeReviewsAdapter(this);
        adapter.set(mAnimeDigest.getReviews());
        mRecyclerView.setAdapter(adapter);

        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetAnimeDigestListener implements ApiResponse<AnimeDigest> {
        private final WeakReference<AnimeReviewsActivity> mActivityReference;

        private GetAnimeDigestListener(final AnimeReviewsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeReviewsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final AnimeDigest animeDigest) {
            final AnimeReviewsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (animeDigest.hasReviews()) {
                    activity.showReviews(animeDigest);
                } else {
                    activity.showEmpty(animeDigest);
                }
            }
        }
    }

}
