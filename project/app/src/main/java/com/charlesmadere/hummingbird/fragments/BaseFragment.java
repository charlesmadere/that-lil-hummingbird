package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.charlesmadere.hummingbird.misc.Heartbeat;
import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements Heartbeat {

    private static final String TAG = "BaseFragment";

    private boolean mIsAlive;
    private Unbinder mUnbinder;


    public abstract String getFragmentName();

    @Override
    public boolean isAlive() {
        return mIsAlive && isAdded() && !isRemoving();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(TAG, '"' + getFragmentName() + "\" created");
    }

    @Override
    public void onDestroyView() {
        mIsAlive = false;

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }

        super.onDestroyView();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mIsAlive = true;
    }

    @Override
    public String toString() {
        return getFragmentName();
    }

}
