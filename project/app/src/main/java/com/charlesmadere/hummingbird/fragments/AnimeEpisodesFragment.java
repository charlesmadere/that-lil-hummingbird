package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeEpisodesAdapter;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;
import com.charlesmadere.hummingbird.models.AnimeEpisode;

import java.util.ArrayList;

import butterknife.Bind;

public class AnimeEpisodesFragment extends BaseFragment {

    private static final String TAG = "AnimeEpisodesFragment";
    private static final String KEY_ANIME_EPISODES = "AnimeEpisodes";

    private ArrayList<AnimeEpisode> mAnimeEpisodes;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeEpisodesFragment create(final ArrayList<AnimeEpisode> animeEpisodes) {
        final Bundle args = new Bundle(1);
        args.putParcelableArrayList(KEY_ANIME_EPISODES, animeEpisodes);

        final AnimeEpisodesFragment fragment = new AnimeEpisodesFragment();
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
        mAnimeEpisodes = args.getParcelableArrayList(KEY_ANIME_EPISODES);
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

        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mAnimeEpisodes == null || mAnimeEpisodes.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            final AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(getContext());
            adapter.setAnimeEpisodes(mAnimeEpisodes);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
