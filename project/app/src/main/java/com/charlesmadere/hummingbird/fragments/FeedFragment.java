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
import com.charlesmadere.hummingbird.adapters.StoriesAdapter;
import com.charlesmadere.hummingbird.misc.SpaceItemDecoration;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Story;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;

public class FeedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FeedFragment";
    private static final String KEY_STORIES = "Stories";
    private static final String KEY_USER = "User";

    @Bind(R.id.llEmpty)
    LinearLayout mEmpty;

    @Bind(R.id.llError)
    LinearLayout mError;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private ArrayList<Story> mStories;
    private StoriesAdapter mAdapter;
    private User mUser;


    public static FeedFragment create(final User user) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_USER, user);

        final FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchActivityFeed() {
        mRefreshLayout.setRefreshing(true);
        Api.getActivityFeed(mUser, new GetCurrentUserActivityFeedListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUser = args.getParcelable(KEY_USER);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mStories = savedInstanceState.getParcelableArrayList(KEY_STORIES);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onRefresh() {
        fetchActivityFeed();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mStories != null && !mStories.isEmpty()) {
            outState.putParcelableArrayList(KEY_STORIES, mStories);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new StoriesAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mStories == null || mStories.isEmpty()) {
            fetchActivityFeed();
        } else {
            showList(mStories);
        }
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

    private void showList(final ArrayList<Story> stories) {
        mStories = stories;
        mAdapter.set(stories);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetCurrentUserActivityFeedListener implements
            ApiResponse<ArrayList<Story>> {
        private final WeakReference<FeedFragment> mFragmentReference;

        private GetCurrentUserActivityFeedListener(final FeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(@Nullable final ArrayList<Story> stories) {
            final FeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (stories == null || stories.isEmpty()) {
                    fragment.showEmpty();
                } else {
                    fragment.showList(stories);
                }
            }
        }
    }

}
