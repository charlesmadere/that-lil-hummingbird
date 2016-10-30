package com.charlesmadere.hummingbird.fragments;

public class FavoriteMangaFragment extends BaseBottomSheetDialogFragment {

    private static final String TAG = "FavoriteMangaFragment";


    public static FavoriteMangaFragment create() {
        return new FavoriteMangaFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

}
