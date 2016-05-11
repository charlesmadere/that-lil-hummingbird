package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class CommentsActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommentsActivity";
    private static final String CNAME = CommentsActivity.class.getCanonicalName();
    private static final String EXTRA_STORY_ID = CNAME + ".StoryId";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private String mStoryId;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String storyId) {
        return new Intent(context, CommentsActivity.class)
                .putExtra(EXTRA_STORY_ID, storyId);
    }

    public void fetchSubstories() {
        mRefreshLayout.setRefreshing(true);
        Api.getSubstories(mStoryId, new GetSubstoriesListener(this));
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
        setContentView(R.layout.activity_comments);

        final Intent intent = getIntent();
        mStoryId = intent.getStringExtra(EXTRA_STORY_ID);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null) {
            fetchSubstories();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_comments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miPostComment:
                // TODO
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        fetchSubstories();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();

        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showFeed(final Feed feed) {
        mFeed = feed;
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetSubstoriesListener implements ApiResponse<Feed> {
        private final WeakReference<CommentsActivity> mActivityReference;

        private GetSubstoriesListener(final CommentsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final CommentsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final CommentsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showFeed(feed);
            }
        }
    }

}
