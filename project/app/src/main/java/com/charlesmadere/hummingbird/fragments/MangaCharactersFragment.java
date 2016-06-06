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

import java.util.ArrayList;

import butterknife.BindView;

public class MangaCharactersFragment extends BaseFragment {

    private static final String TAG = "MangaDetailsFragment";
    private static final String KEY_CHARACTERS = "Characters";

    private ArrayList<MangaDigest.Character> mCharacters;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static MangaCharactersFragment create(final ArrayList<MangaDigest.Character> characters) {
        final Bundle args = new Bundle(1);
        args.putParcelableArrayList(KEY_CHARACTERS, characters);

        final MangaCharactersFragment fragment = new MangaCharactersFragment();
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
        mCharacters = args.getParcelableArrayList(KEY_CHARACTERS);
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

        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mCharacters == null || mCharacters.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            final MangaCharactersAdapter adapter = new MangaCharactersAdapter(getContext());
            adapter.set(mCharacters);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
