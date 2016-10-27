package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeReviewsAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import butterknife.BindView;

public class AnimeReviewsFragment extends BaseAnimeFragment {

    private static final String TAG = "AnimeReviewsFragment";

    private AnimeReviewsAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeReviewsFragment create() {
        return new AnimeReviewsFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_reviews, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
        mAdapter = new AnimeReviewsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void showAnimeDigest(final AnimeDigest animeDigest) {
        if (animeDigest.hasReviews()) {
            mAdapter.set(animeDigest.getReviews());
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mAdapter.set(null);
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

}
