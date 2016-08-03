package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaCharactersAdapter;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class MangaCharactersFragment extends BaseMangaFragment {

    private static final String TAG = "MangaDetailsFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static MangaCharactersFragment create() {
        return new MangaCharactersFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_characters, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);

        final MangaDigest mangaDigest = getMangaDigest();

        if (mangaDigest.hasCharacters()) {
            final MangaCharactersAdapter adapter = new MangaCharactersAdapter(getContext());
            adapter.set(mangaDigest.getCharacters());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

}
