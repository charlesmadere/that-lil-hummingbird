package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeEpisodesAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class AnimeEpisodesFragment extends BaseAnimeFragment {

    private static final String TAG = "AnimeEpisodesFragment";

    private AnimeEpisodesAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeEpisodesFragment create() {
        return new AnimeEpisodesFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_episodes, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new AnimeEpisodesAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void showAnimeDigest(final AnimeDigest animeDigest) {
        if (animeDigest.hasEpisodes()) {
            mAdapter.set(animeDigest.getEpisodes());
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mAdapter.set(null);
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

}
