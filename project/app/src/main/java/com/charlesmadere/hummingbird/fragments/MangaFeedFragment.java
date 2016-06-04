package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;

public class MangaFeedFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MangaDetailsFragment";
    private static final String KEY_FEED = "Feed";
    private static final String KEY_MANGA_ID = "MangaId";

    private Feed mFeed;
    private String mMangaId;


    public static MangaFeedFragment create(final String mangaId) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_MANGA_ID, mangaId);

        final MangaFeedFragment fragment = new MangaFeedFragment();
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
        mMangaId = args.getString(KEY_MANGA_ID);

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

    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
