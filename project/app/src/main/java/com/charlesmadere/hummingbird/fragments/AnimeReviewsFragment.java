package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeReviewsAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.views.DividerItemDecoration;

import butterknife.BindView;

public class AnimeReviewsFragment extends BaseAnimeFragment {

    private static final String TAG = "AnimeReviewsFragment";

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
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration.apply(mRecyclerView);

        final AnimeDigest animeDigest = getAnimeDigest();

        if (animeDigest.hasReviews()) {
            final AnimeReviewsAdapter adapter = new AnimeReviewsAdapter(getContext());
            adapter.set(animeDigest.getReviews());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_reviews, container, false);
    }

}
