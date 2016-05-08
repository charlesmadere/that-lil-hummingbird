package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class UserDigestFragment extends BaseFragment {

    private static final String TAG = "UserDigestFragment";
    private static final String KEY_USERNAME = "Username";

    private UserDigest mUserDigest;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static UserDigestFragment create(final String username) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, username);

        final UserDigestFragment fragment = new UserDigestFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_digest, container, false);
    }

}
