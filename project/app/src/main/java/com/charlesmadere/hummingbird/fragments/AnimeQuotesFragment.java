package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeQuotesAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.views.DividerItemDecoration;

import butterknife.BindView;

public class AnimeQuotesFragment extends BaseAnimeFragment {

    private static final String TAG = "AnimeQuotesFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeQuotesFragment create() {
        return new AnimeQuotesFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_quotes, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration.apply(mRecyclerView);

        final AnimeDigest animeDigest = getAnimeDigest();

        if (animeDigest.hasQuotes()) {
            final AnimeQuotesAdapter adapter = new AnimeQuotesAdapter(getContext());
            adapter.set(animeDigest.getQuotes());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

}
