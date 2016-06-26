package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPublicPrivateView extends LinearLayout {

    private OnSelectionChangedListener mListener;

    @BindView(R.id.rbPrivate)
    RadioButton mPrivate;

    @BindView(R.id.rbPublic)
    RadioButton mPublic;


    public ModifyPublicPrivateView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyPublicPrivateView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ModifyPublicPrivateView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isPrivateChecked() {
        return mPrivate.isChecked();
    }

    public boolean isPublicChecked() {
        return mPublic.isChecked();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.llPrivate)
    void onPrivateClick() {
        update(false, true);
    }

    @OnClick(R.id.llPublic)
    void onPublicClick() {
        update(true, false);
    }

    public void setOnSelectionChangedListener(@Nullable final OnSelectionChangedListener l) {
        mListener = l;
    }

    private void update(final boolean _public, final boolean _private) {
        mPublic.setChecked(_public);
        mPrivate.setChecked(_private);

        if (mListener != null) {
            mListener.onSelectionChanged(this);
        }
    }


    public interface OnSelectionChangedListener {
        void onSelectionChanged(final ModifyPublicPrivateView v);
    }

}
