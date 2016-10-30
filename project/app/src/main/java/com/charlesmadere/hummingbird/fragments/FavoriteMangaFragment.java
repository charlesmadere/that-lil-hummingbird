package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;

public class FavoriteMangaFragment extends BaseBottomSheetDialogFragment implements
        View.OnClickListener {

    private static final String TAG = "FavoriteMangaFragment";


    public static FavoriteMangaFragment create() {
        return new FavoriteMangaFragment();
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
        return inflater.inflate(R.layout.fragment_favorite_manga, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO
    }

}
