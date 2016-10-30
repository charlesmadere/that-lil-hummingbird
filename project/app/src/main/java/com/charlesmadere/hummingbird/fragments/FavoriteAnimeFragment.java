package com.charlesmadere.hummingbird.fragments;

public class FavoriteAnimeFragment extends BaseBottomSheetDialogFragment {

    private static final String TAG = "FavoriteAnimeFragment";


    public static FavoriteAnimeFragment create() {
        return new FavoriteAnimeFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

}
