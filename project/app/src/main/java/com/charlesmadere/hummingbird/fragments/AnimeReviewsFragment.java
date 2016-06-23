package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeReviewsAdapter;
import com.charlesmadere.hummingbird.models.AnimeReview;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

public class AnimeReviewsFragment extends BaseFragment {

    private static final String TAG = "AnimeReviewsFragment";
    private static final String KEY_REVIEWS = "Reviews";

    private ArrayList<AnimeReview> mReviews;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeReviewsFragment create(final ArrayList<AnimeReview> reviews) {
        final Bundle args = new Bundle(1);
        args.putParcelableArrayList(KEY_REVIEWS, reviews);

        final AnimeReviewsFragment fragment = new AnimeReviewsFragment();
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
        mReviews = args.getParcelableArrayList(KEY_REVIEWS);
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

        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mReviews == null || mReviews.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            final AnimeReviewsAdapter adapter = new AnimeReviewsAdapter(getContext());
            adapter.set(mReviews);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
