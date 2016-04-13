package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private boolean mIsDestroyed;


    public abstract String getFragmentName();

    public boolean isDestroyed() {
        return mIsDestroyed;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(getFragmentName(), '"' + getFragmentName() + "\" created");
        mIsDestroyed = false;
    }

    @Override
    public void onDestroy() {
        mIsDestroyed = true;
        super.onDestroy();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

}
