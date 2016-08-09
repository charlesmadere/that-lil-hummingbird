package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class StoryActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "StoryActivity";
    private static final String CNAME = StoryActivity.class.getCanonicalName();
    private static final String EXTRA_STORY_ID = CNAME + ".StoryId";

    private Feed mFeed;
    private FeedAdapter mAdapter;
    private String mStoryId;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String storyId) {
        return new Intent(context, StoryActivity.class)
                .putExtra(EXTRA_STORY_ID, storyId);
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
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mStoryId };
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        final Intent intent = getIntent();
        mStoryId = intent.getStringExtra(EXTRA_STORY_ID);

        mFeed = ObjectCache.get(this);

        if (mFeed == null || !mFeed.hasStories()) {
            fetchFeed();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchFeed();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new FeedAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showError() {
        // TODO
        mRefreshLayout.setRefreshing(false);
    }

    private void showFeed(final Feed feed) {
        // TODO
        mRefreshLayout.setRefreshing(false);
    }

}
