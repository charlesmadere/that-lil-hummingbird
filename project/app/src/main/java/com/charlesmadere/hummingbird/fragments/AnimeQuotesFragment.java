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
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

public class AnimeQuotesFragment extends BaseFragment {

    private static final String TAG = "AnimeQuotesFragment";
    private static final String KEY_QUOTES = "Quotes";

    private ArrayList<AnimeDigest.Quote> mQuotes;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeQuotesFragment create(final ArrayList<AnimeDigest.Quote> quotes) {
        final Bundle args = new Bundle(1);
        args.putParcelableArrayList(KEY_QUOTES, quotes);

        final AnimeQuotesFragment fragment = new AnimeQuotesFragment();
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
        mQuotes = args.getParcelableArrayList(KEY_QUOTES);
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
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding);

        if (mQuotes == null || mQuotes.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            final AnimeQuotesAdapter adapter = new AnimeQuotesAdapter(getContext());
            adapter.set(mQuotes);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
