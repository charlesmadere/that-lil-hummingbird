package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GroupsAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserGroupsFragment extends BaseFragment implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserGroupsFragment";
    private static final String KEY_USERNAME = "Username";

    private Feed mFeed;
    private GroupsAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;
    private String mUsername;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static UserGroupsFragment create() {
        return create(CurrentUser.get().getUserId());
    }

    public static UserGroupsFragment create(final String username) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, username);

        final UserGroupsFragment fragment = new UserGroupsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchUserGroups() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserGroups(mUsername, new GetUserGroupsListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), mUsername};
    }

    @Override
    public boolean isLoading() {
        return mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUsername = args.getString(KEY_USERNAME);

        mFeed = ObjectCache.get(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_groups, container, false);
    }

    @Override
    public void onRefresh() {
        fetchUserGroups();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new GroupsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);

        if (mFeed == null) {
            fetchUserGroups();
        } else {
            showUserGroups(mFeed);
        }
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getUserGroups(mUsername, mFeed, new PaginateUserGroupsListener(this));
    }

    private void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    private void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showUserGroups(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(mFeed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetUserGroupsListener implements ApiResponse<Feed> {
        private final WeakReference<UserGroupsFragment> mFragmentReference;

        private GetUserGroupsListener(final UserGroupsFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserGroupsFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserGroupsFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasGroups()) {
                    fragment.showUserGroups(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

    private static class PaginateUserGroupsListener implements ApiResponse<Feed> {
        private final WeakReference<UserGroupsFragment> mFragmentReference;
        private final int mGroupsSize;

        private PaginateUserGroupsListener(final UserGroupsFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mGroupsSize = fragment.mFeed.getGroupsSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserGroupsFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserGroupsFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasCursor() && feed.getGroupsSize() > mGroupsSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

}
