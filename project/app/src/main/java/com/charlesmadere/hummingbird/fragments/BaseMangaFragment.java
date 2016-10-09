package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.MangaDigest;

public abstract class BaseMangaFragment extends BaseFragment {

    private Listener mListener;


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MangaDigest mangaDigest = mListener.getMangaDigest();

        if (mangaDigest != null) {
            showMangaDigest(mangaDigest);
        }
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else {
            final Activity activity = MiscUtils.optActivity(context);

            if (activity instanceof Listener) {
                mListener = (Listener) activity;
            }
        }

        if (mListener == null) {
            throw new IllegalStateException(getFragmentName() + " must attach to Listener");
        }
    }

    public final void showMangaDigest() {
        showMangaDigest(mListener.getMangaDigest());
    }

    public abstract void showMangaDigest(final MangaDigest mangaDigest);


    public interface Listener {
        MangaDigest getMangaDigest();
    }

}
