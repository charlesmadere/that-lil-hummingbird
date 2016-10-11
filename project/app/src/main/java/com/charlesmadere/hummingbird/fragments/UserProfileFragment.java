package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AboutUserView;
import com.charlesmadere.hummingbird.views.AnimeBreakdownView;
import com.charlesmadere.hummingbird.views.FavoriteAnimeView;
import com.charlesmadere.hummingbird.views.FavoriteMangaView;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.UserBioView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserProfileFragment extends BaseUserFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserDigestFragment";

    @BindView(R.id.aboutUserView)
    AboutUserView mAboutUserView;

    @BindView(R.id.animeBreakdownView)
    AnimeBreakdownView mAnimeBreakdownView;

    @BindView(R.id.favoriteAnimeView)
    FavoriteAnimeView mFavoriteAnimeView;

    @BindView(R.id.favoriteMangaView)
    FavoriteMangaView mFavoriteMangaView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.animeBreakdownSpace)
    Space mAnimeBreakdownSpace;

    @BindView(R.id.userBioView)
    UserBioView mUserBioView;


    public static UserProfileFragment create() {
        return new UserProfileFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onRefresh() {
        refreshUserDigest();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
    }

    private void refreshUserDigest() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserDigest(getUserId(), new GetUserDigestListener(this));
    }

    private void showRefreshError() {
        Toast.makeText(getContext(), R.string.error_refreshing_user, Toast.LENGTH_LONG).show();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void showUserDigest(final UserDigest userDigest) {
        mAboutUserView.setContent(userDigest);

        if (userDigest.getInfo().hasTopGenres()) {
            mAnimeBreakdownView.setContent(userDigest);
            mAnimeBreakdownView.setVisibility(View.VISIBLE);
            mAnimeBreakdownSpace.setVisibility(View.VISIBLE);
        } else {
            mAnimeBreakdownView.setVisibility(View.GONE);
            mAnimeBreakdownSpace.setVisibility(View.GONE);
        }

        mUserBioView.setContent(userDigest);
        mFavoriteAnimeView.setContent(userDigest);
        mFavoriteMangaView.setContent(userDigest);

        mRefreshLayout.setRefreshing(false);
    }


    private static class GetUserDigestListener implements ApiResponse<UserDigest> {
        private final WeakReference<UserProfileFragment> mFragmentReference;

        private GetUserDigestListener(final UserProfileFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserProfileFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showRefreshError();
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final UserProfileFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.setUserDigest(userDigest);
            }
        }
    }

}
