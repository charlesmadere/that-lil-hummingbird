package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.GroupDigest;

public abstract class BaseGroupFragment extends BaseFragment {

    private Listeners mListeners;


    protected GroupDigest getGroupDigest() {
        return mListeners.getGroupDigest();
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

    protected void setGroupDigest(final GroupDigest groupDigest) {
        mListeners.setGroupDigest(groupDigest);
    }


    public interface Listeners {
        GroupDigest getGroupDigest();
        void setGroupDigest(final GroupDigest groupDigest);
    }

}
