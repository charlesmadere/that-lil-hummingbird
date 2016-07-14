package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class AnimeReviewsActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AnimeReviewsActivity";
    private static final String CNAME = AnimeReviewsActivity.class.getCanonicalName();
    private static final String EXTRA_ANIME_ID = CNAME + ".AnimeId";
    private static final String EXTRA_ANIME_TITLE = CNAME + ".AnimeTitle";
    private static final String KEY_ANIME_REVIEWS = "AnimeReviews";

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

    @Override
    public String getActivityName() {
        return TAG;
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

        // TODO
    }

    @Override
    public void onRefresh() {
        // TODO
    }

}
