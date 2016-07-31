package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeReview;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AnimeReviewVerdictView;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.UserItemView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class AnimeReviewActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AnimeReviewActivity";
    private static final String CNAME = AnimeReviewActivity.class.getCanonicalName();
    private static final String EXTRA_ANIME_ID = CNAME + ".AnimeId";
    private static final String EXTRA_ANIME_REVIEW_ID = CNAME + ".AnimeReviewId";
    private static final String EXTRA_ANIME_REVIEW = CNAME + ".AnimeReview";

    private AnimeDigest mAnimeDigest;
    private AnimeReview mAnimeReview;
    private String mAnimeId;
    private String mAnimeReviewId;

    @BindView(R.id.animeReviewVerdictView)
    AnimeReviewVerdictView mAnimeReviewVerdictView;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.tvContent)
    TextView mContent;

    @BindView(R.id.userItemView)
    UserItemView mUserItemView;


    public static Intent getLaunchIntent(final Context context, final AnimeReview review) {
        return new Intent(context, AnimeReviewActivity.class)
                .putExtra(EXTRA_ANIME_REVIEW, review);
    }

    public static Intent getLaunchIntent(final Context context, final String animeId,
            final String animeReviewId) {
        return new Intent(context, AnimeReviewActivity.class)
                .putExtra(EXTRA_ANIME_ID, animeId)
                .putExtra(EXTRA_ANIME_REVIEW_ID, animeReviewId);
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
        return new String[] { getActivityName(), mAnimeId, mAnimeReviewId };
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_review);

        final Intent intent = getIntent();
        mAnimeId = intent.getStringExtra(EXTRA_ANIME_ID);
        mAnimeReviewId = intent.getStringExtra(EXTRA_ANIME_REVIEW_ID);

        if (intent.hasExtra(EXTRA_ANIME_REVIEW)) {
            mAnimeReview = intent.getParcelableExtra(EXTRA_ANIME_REVIEW);
        } else {
            mAnimeDigest = ObjectCache.get(this);
        }

        if (mAnimeReview != null) {
            showAnimeReview(mAnimeReview);
        } else if (mAnimeDigest != null) {
            showAnimeDigest(mAnimeDigest);
        } else {
            fetchAnimeDigest();
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
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void showAnimeDigest(final AnimeDigest animeDigest) {
        mAnimeDigest = animeDigest;

        if (!mAnimeDigest.hasReviews()) {
            showError();
            return;
        }

        for (final AnimeReview animeReview : mAnimeDigest.getReviews()) {
            if (mAnimeReviewId.equalsIgnoreCase(animeReview.getId())) {
                showAnimeReview(animeReview);
                return;
            }
        }

        showError();
    }

    private void showAnimeReview(final AnimeReview animeReview) {
        mAnimeReview = animeReview;
        setSubtitle(mAnimeReview.getAnimeTitle());
        mUserItemView.setContent(mAnimeReview.getUser());
        mContent.setText(mAnimeReview.getContent());
        mAnimeReviewVerdictView.setContent(mAnimeReview);

        mError.setVisibility(View.GONE);
        mScrollView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setEnabled(false);
    }

    private void showError() {
        mScrollView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetAnimeDigestListener implements ApiResponse<AnimeDigest> {
        private final WeakReference<AnimeReviewActivity> mActivityReference;

        private GetAnimeDigestListener(final AnimeReviewActivity animeReviewActivity) {
            mActivityReference = new WeakReference<>(animeReviewActivity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeReviewActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final AnimeDigest animeDigest) {
            final AnimeReviewActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showAnimeDigest(animeDigest);
            }
        }
    }

}
