package com.charlesmadere.hummingbird.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.FrameLayout;

import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "BaseBottomSheetDialogFragment";

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

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        if (startFullyExpanded()) {
            final FrameLayout bottomSheet = (FrameLayout) dialog.findViewById(
                    android.support.design.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }

        return dialog;
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

    protected boolean startFullyExpanded() {
        return false;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mIsDestroyed = false;
    }

}
