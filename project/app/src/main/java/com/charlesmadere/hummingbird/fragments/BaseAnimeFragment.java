package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeDigest;

public abstract class BaseAnimeFragment extends BaseFragment {

    private Listener mListener;


    protected AnimeDigest getAnimeDigest() {
        return mListener.getAnimeDigest();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AnimeDigest animeDigest = mListener.getAnimeDigest();

        if (animeDigest != null) {
            showAnimeDigest(animeDigest);
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

    public final void showAnimeDigest() {
        showAnimeDigest(mListener.getAnimeDigest());
    }

    protected abstract void showAnimeDigest(final AnimeDigest animeDigest);


    public interface Listener {
        AnimeDigest getAnimeDigest();
    }

}
