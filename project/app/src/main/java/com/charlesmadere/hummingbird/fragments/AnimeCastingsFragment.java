package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeCastingsAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

public class AnimeCastingsFragment extends BaseFragment {

    private static final String TAG = "AnimeCastingsFragment";
    private static final String KEY_CASTINGS = "Castings";

    private ArrayList<AnimeDigest.Casting> mCastings;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeCastingsFragment create(final ArrayList<AnimeDigest.Casting> castings) {
        final Bundle args = new Bundle(1);
        args.putParcelableArrayList(KEY_CASTINGS, castings);

        final AnimeCastingsFragment fragment = new AnimeCastingsFragment();
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
        mCastings = args.getParcelableArrayList(KEY_CASTINGS);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_castings, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mCastings == null || mCastings.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            final AnimeCastingsAdapter adapter = new AnimeCastingsAdapter(getContext());
            adapter.set(mCastings);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
