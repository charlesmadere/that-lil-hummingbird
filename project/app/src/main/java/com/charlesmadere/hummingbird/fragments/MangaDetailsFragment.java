package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.MangaDigest;

public class MangaDetailsFragment extends BaseFragment {

    private static final String TAG = "MangaDetailsFragment";
    private static final String KEY_MANGA_DIGEST = "MangaDigest";

    private MangaDigest mMangaDigest;


    public static MangaDetailsFragment create(final MangaDigest digest) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_MANGA_DIGEST, digest);

        final MangaDetailsFragment fragment = new MangaDetailsFragment();
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
        mMangaDigest = args.getParcelable(KEY_MANGA_DIGEST);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
