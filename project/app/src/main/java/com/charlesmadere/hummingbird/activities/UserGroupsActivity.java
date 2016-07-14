package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserGroupsActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserGroupsActivity";
    private static final String CNAME = UserGroupsActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private String mUsername;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return new Intent(context, UserGroupsActivity.class)
                .putExtra(EXTRA_USERNAME, username);
    }

    private void fetchUserGroups() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserGroups(mUsername, new GetUserGroupsListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_groups);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setSubtitle(mUsername);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null || !mFeed.hasGroups()) {
            fetchUserGroups();
        } else {
            showUserGroups(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchUserGroups();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null && mFeed.hasGroups()) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    private void showEmpty() {
        // TODO
    }

    private void showError() {
        // TODO
    }

    private void showUserGroups(final Feed feed) {
        // TODO
    }


    private static class GetUserGroupsListener implements ApiResponse<Feed> {
        private final WeakReference<UserGroupsActivity> mActivityReference;

        private GetUserGroupsListener(final UserGroupsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserGroupsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                // TODO
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserGroupsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                // TODO
            }
        }
    }

}
