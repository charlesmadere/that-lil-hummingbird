package com.charlesmadere.hummingbird.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment implements
        DialogInterface.OnShowListener {

    private static final String TAG = "BaseBottomSheetDialogFragment";

    private boolean mIsAlive;
    private Unbinder mUnbinder;


    private void autoExpand() {
        final Dialog dialog = getDialog();

        if (dialog == null) {
            return;
        }

        final Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        final CoordinatorLayout coordinatorLayout = findCoordinatorLayout(window.getDecorView());

        if (coordinatorLayout == null) {
            return;
        }

        View view = null;

        for (int i = 0; i < coordinatorLayout.getChildCount(); ++i) {
            view = coordinatorLayout.getChildAt(i);

            if (view instanceof ViewGroup) {
                break;
            }
        }

        if (view == null) {
            return;
        }

        final ViewGroup.LayoutParams vglp = view.getLayoutParams();

        if (vglp instanceof CoordinatorLayout.LayoutParams) {
            final CoordinatorLayout.LayoutParams cllp = (CoordinatorLayout.LayoutParams) vglp;

            if (cllp.getBehavior() instanceof BottomSheetBehavior) {
                final BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                cllp.setBehavior(behavior);
                view.setLayoutParams(cllp);
            }
        }
    }

    @Nullable
    private CoordinatorLayout findCoordinatorLayout(@Nullable final View view) {
        if (view instanceof CoordinatorLayout) {
            return (CoordinatorLayout) view;
        } else if (view instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                final View v = viewGroup.getChildAt(i);
                final CoordinatorLayout coordinatorLayout = findCoordinatorLayout(v);

                if (coordinatorLayout != null) {
                    return coordinatorLayout;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public abstract String getFragmentName();

    public boolean isAlive() {
        return mIsAlive;
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
        dialog.setOnShowListener(this);

        return dialog;
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
    public void onShow(final DialogInterface dialog) {
        if (shouldAutoExpand()) {
            autoExpand();
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mIsAlive = true;
    }

    protected boolean shouldAutoExpand() {
        return false;
    }

}
