package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;

public class FavoriteAnimeFragment extends BaseBottomSheetDialogFragment implements
        View.OnClickListener {

    private static final String TAG = "FavoriteAnimeFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static FavoriteAnimeFragment create() {
        return new FavoriteAnimeFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onClick(final View view) {
        dismissAllowingStateLoss();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_favorite_anime, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        mToolbar.setNavigationOnClickListener(this);
        mToolbar.setTitle(R.string.favorite_anime);
    }

}
