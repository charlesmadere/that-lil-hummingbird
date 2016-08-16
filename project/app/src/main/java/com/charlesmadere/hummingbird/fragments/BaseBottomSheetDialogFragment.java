package com.charlesmadere.hummingbird.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "BaseBottomSheetDialogFragment";

    private boolean mIsDestroyed;
    private Unbinder mUnbinder;


    private void autoExpand() {
        final CoordinatorLayout coordinatorLayout = findCoordinatorLayout(
                getDialog().getWindow().getDecorView());

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
            final CoordinatorLayout.LayoutParams cllp = (CoordinatorLayout.LayoutParams)
                    view.getLayoutParams();
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            cllp.setBehavior(behavior);
            view.setLayoutParams(cllp);
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

    public boolean isDestroyed() {
        return mIsDestroyed;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(TAG, '"' + getFragmentName() + "\" created");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                autoExpand();
            }
        });

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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mIsDestroyed = false;
    }

}
