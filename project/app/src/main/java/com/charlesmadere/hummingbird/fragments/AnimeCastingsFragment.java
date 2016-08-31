package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeCastingsAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import butterknife.BindView;

public class AnimeCastingsFragment extends BaseAnimeFragment {

    private static final String TAG = "AnimeCastingsFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeCastingsFragment create() {
        return new AnimeCastingsFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setHasFixedSize(true);

        final AnimeDigest animeDigest = getAnimeDigest();

        if (animeDigest.hasCastings()) {
            final AnimeCastingsAdapter adapter = new AnimeCastingsAdapter(getContext());
            adapter.set(animeDigest.getCastings());
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
        return inflater.inflate(R.layout.fragment_anime_castings, container, false);
    }

}
