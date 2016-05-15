package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.AbsUser;
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

public class UserDigestFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserDigestFragment";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_USER_DIGEST = "UserDigest";

    private String mUsername;
    private UserDigest mUserDigest;

    @BindView(R.id.aboutUserView)
    AboutUserView mAboutUserView;

    @BindView(R.id.animeBreakdownView)
    AnimeBreakdownView mAnimeBreakdownView;

    @BindView(R.id.favoriteAnimeView)
    FavoriteAnimeView mFavoriteAnimeView;

    @BindView(R.id.favoriteMangaView)
    FavoriteMangaView mFavoriteMangaView;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    @BindView(R.id.userBioView)
    UserBioView mUserBioView;


    public static UserDigestFragment create() {
        final AbsUser currentUser = CurrentUser.get();
        return create(currentUser.getName());
    }

    public static UserDigestFragment create(final String username) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, username);

        final UserDigestFragment fragment = new UserDigestFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchUserDigest() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserDigest(mUsername, new GetUserDigestListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUsername = args.getString(KEY_USERNAME);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mUserDigest = savedInstanceState.getParcelable(KEY_USER_DIGEST);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_digest, container, false);
    }

    @Override
    public void onRefresh() {
        fetchUserDigest();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);

        if (mUserDigest == null) {
            fetchUserDigest();
        } else {
            showUserDigest(mUserDigest);
        }
    }

    private void showError() {
        mScrollView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showUserDigest(final UserDigest userDigest) {
        mUserDigest = userDigest;
        mError.setVisibility(View.GONE);
        mScrollView.setVisibility(View.VISIBLE);
        mAboutUserView.setContent(userDigest);
        mAnimeBreakdownView.setContent(userDigest);
        mUserBioView.setContent(userDigest);
        mFavoriteAnimeView.setContent(userDigest);
        mFavoriteMangaView.setContent(userDigest);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetUserDigestListener implements ApiResponse<UserDigest> {
        private final WeakReference<UserDigestFragment> mFragmentReference;

        private GetUserDigestListener(final UserDigestFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserDigestFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final UserDigestFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showUserDigest(userDigest);
            }
        }
    }

}
