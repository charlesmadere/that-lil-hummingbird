package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    private boolean mIsDestroyed;
    private Unbinder mUnbinder;


    public abstract String getFragmentName();

    public boolean isDestroyed() {
        return mIsDestroyed;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(TAG, '"' + getFragmentName() + "\" created");
    }

    @Override
    public void onDestroyView() {
        mIsDestroyed = true;

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
        mIsDestroyed = false;
    }

}
