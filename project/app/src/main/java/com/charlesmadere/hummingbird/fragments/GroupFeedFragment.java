package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class GroupFeedFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupFeedFragment";
    private static final String KEY_FEED = "Feed";
    private static final String KEY_GROUP_ID = "GroupId";

    private Feed mFeed;
    private FeedAdapter mAdapter;
    private String mGroupId;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static GroupFeedFragment create(final String groupId) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_GROUP_ID, groupId);

        final GroupFeedFragment fragment = new GroupFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mGroupId = args.getString(KEY_GROUP_ID);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
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
        mRefreshLayout.setRefreshing(true);

    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

}
