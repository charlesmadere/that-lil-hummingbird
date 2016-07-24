package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class MediaStoryActivity extends BaseDrawerActivity implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaStoryActivity";
    private static final String CNAME = MediaStoryActivity.class.getCanonicalName();
    private static final String EXTRA_MEDIA_STORY = CNAME + ".MediaStory";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private MediaStory mMediaStory;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final MediaStory mediaStory) {
        return new Intent(context, MediaStoryActivity.class)
                .putExtra(EXTRA_MEDIA_STORY, mediaStory);
    }

    private void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
        // TODO
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public boolean isLoading() {
        // TODO
        return false;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_story);

        final Intent intent = getIntent();
        mMediaStory = intent.getParcelableExtra(EXTRA_MEDIA_STORY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        // TODO
    }

    @Override
    public void onRefresh() {
        fetchFeed();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null && mFeed.hasSubstories()) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void paginate() {
        // TODO
    }

    private void showError() {
        // TODO
    }

    private void showFeed(final Feed feed) {
        // TODO
    }

}
