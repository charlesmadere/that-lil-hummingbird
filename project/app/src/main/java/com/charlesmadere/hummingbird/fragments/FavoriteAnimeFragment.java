package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;

public class FavoriteAnimeFragment extends BaseBottomSheetDialogFragment {

    private static final String TAG = "FavoriteAnimeFragment";


    public static FavoriteAnimeFragment create() {
        return new FavoriteAnimeFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_favorite_anime, container, false);
    }

}
