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
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.UserV1;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

public class FavoriteAnimeFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FavoriteAnimeFragment";
    private static final String KEY_FAVORITES = "Favorites";
    private static final String KEY_USER = "User";

    private AnimeAdapter mAdapter;
    private ArrayList<AbsAnime> mFavorites;
    private UserV1 mUser;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static FavoriteAnimeFragment create(final UserV1 user) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_USER, user);

        final FavoriteAnimeFragment fragment = new FavoriteAnimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchFavorites() {
        mRefreshLayout.setRefreshing(true);
        Api.getFavoriteAnime(mUser.getName(), new GetFavoritesListener(this));
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
            mFavorites = savedInstanceState.getParcelableArrayList(KEY_FAVORITES);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_favorite_anime, container, false);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFavorites != null && !mFavorites.isEmpty()) {
            outState.putParcelableArrayList(KEY_FAVORITES, mFavorites);
        }
    }

    @Override
    public void onRefresh() {
        fetchFavorites();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AnimeAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mFavorites == null || mFavorites.isEmpty()) {
            fetchFavorites();
        } else {
            showList(mFavorites);
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

    private void showList(final ArrayList<AbsAnime> favorites) {
        mFavorites = favorites;
        mAdapter.set(favorites);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetFavoritesListener implements ApiResponse<ArrayList<AbsAnime>> {
        private final WeakReference<FavoriteAnimeFragment> mFragmentReference;

        private GetFavoritesListener(final FavoriteAnimeFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FavoriteAnimeFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(@Nullable final ArrayList<AbsAnime> favorites) {
            final FavoriteAnimeFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (favorites == null || favorites.isEmpty()) {
                    fragment.showEmpty();
                } else {
                    fragment.showList(favorites);
                }
            }
        }
    }

}
