package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeDigest;

public abstract class BaseAnimeFragment extends BaseFragment {

    private Listener mListener;


    protected AnimeDigest getAnimeDigest() {
        return mListener.getAnimeDigest();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else {
            final Activity activity = MiscUtils.getActivity(context);

            if (activity instanceof Listener) {
                mListener = (Listener) activity;
            }
        }

        if (mListener == null) {
            throw new IllegalStateException(getFragmentName() + " must have a Listener");
        }
    }


    public interface Listener {
        AnimeDigest getAnimeDigest();
    }

}
