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
import com.charlesmadere.hummingbird.adapters.AnimeAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Franchise;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class AnimeFranchiseFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AnimeFranchiseFragment";
    private static final String KEY_FRANCHISE = "Franchise";
    private static final String KEY_FRANCHISE_ID = "FranchiseId";

    private AnimeAdapter mAdapter;
    private Franchise mFranchise;
    private String mFranchiseId;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static AnimeFranchiseFragment create(final String franchiseId) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_FRANCHISE_ID, franchiseId);

        final AnimeFranchiseFragment fragment = new AnimeFranchiseFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchFranchise() {
        mRefreshLayout.setRefreshing(true);
        Api.getFranchise(mFranchiseId, new GetFranchiseListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mFranchiseId = args.getString(KEY_FRANCHISE_ID);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFranchise = savedInstanceState.getParcelable(KEY_FRANCHISE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_franchise, container, false);
    }

    @Override
    public void onRefresh() {
        fetchFranchise();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFranchise != null) {
            outState.putParcelable(KEY_FRANCHISE, mFranchise);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding);
        mAdapter = new AnimeAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        if (mFranchise == null) {
            fetchFranchise();
        } else if (mFranchise.hasAnime()) {
            showFranchise(mFranchise);
        } else {
            showEmpty();
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

    private void showFranchise(final Franchise franchise) {
        mFranchise = franchise;
        mAdapter.set(mFranchise.getAnime());
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setEnabled(false);
    }


    private static class GetFranchiseListener implements ApiResponse<Franchise> {
        private final WeakReference<AnimeFranchiseFragment> mFragmentReference;

        private GetFranchiseListener(final AnimeFranchiseFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeFranchiseFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(@Nullable final Franchise franchise) {
            final AnimeFranchiseFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (franchise == null || !franchise.hasAnime()) {
                    fragment.showEmpty();
                } else {
                    fragment.showFranchise(franchise);
                }
            }
        }
    }

}
