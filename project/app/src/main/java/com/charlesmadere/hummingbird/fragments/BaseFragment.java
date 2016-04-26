package com.charlesmadere.hummingbird.fragments;

import android.content.Intent;
import android.net.Uri;
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
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    protected void openUrl(final String url) {
        startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(url)));
    }

}
