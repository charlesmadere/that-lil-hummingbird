package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.UserDigest;

public abstract class BaseUserFragment extends BaseFragment {

    private Listeners mListeners;


    protected UserDigest getUserDigest() {
        return mListeners.getUserDigest();
    }

    protected String getUserId() {
        return getUserDigest().getUserId();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final UserDigest userDigest = mListeners.getUserDigest();

        if (userDigest != null) {
            showUserDigest(userDigest);
        }
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listeners) {
            mListeners = (Listeners) fragment;
        } else {
            final Activity activity = MiscUtils.optActivity(context);

            if (activity instanceof Listeners) {
                mListeners = (Listeners) activity;
            }
        }

        if (mListeners == null) {
            throw new IllegalStateException(getFragmentName() + " must attach to Listeners");
        }
    }

    protected void setUserDigest(final UserDigest userDigest) {
        mListeners.setUserDigest(userDigest);
    }

    public final void showUserDigest() {
        showUserDigest(mListeners.getUserDigest());
    }

    protected abstract void showUserDigest(final UserDigest userDigest);


    public interface Listeners {
        UserDigest getUserDigest();
        void setUserDigest(final UserDigest userDigest);
    }

}
