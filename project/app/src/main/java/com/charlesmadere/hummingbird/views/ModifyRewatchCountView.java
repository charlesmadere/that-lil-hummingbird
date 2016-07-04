package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyRewatchCountView extends LinearLayout {

    private int mCount;
    private NumberFormat mNumberFormat;
    private OnRewatchCountChangedListener mListener;

    @BindView(R.id.ibDecreaseCount)
    ImageButton mDecrease;

    @BindView(R.id.tvRewatchCount)
    TextView mRewatchCount;


    public ModifyRewatchCountView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyRewatchCountView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ModifyRewatchCountView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getCount() {
        return mCount;
    }

    private void notifyListener() {
        if (mListener != null) {
            mListener.onRewatchCountChanged(this);
        }
    }

    @OnClick(R.id.ibDecreaseCount)
    void onDecreaseClick() {
        if (mCount < 1) {
            return;
        }

        --mCount;
        update();
        notifyListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @OnClick(R.id.ibIncreaseCount)
    void onIncreaseClick() {
        ++mCount;
        update();
        notifyListener();
    }

    public void setContent(final AnimeLibraryUpdate libraryUpdate) {
        setCount(libraryUpdate.getRewatchCount());
    }

    public void setCount(int count) {
        if (count < 0) {
            count = 0;
        }

        mCount = count;
        update();
    }

    public void setOnRewatchCountChangedListener(@Nullable final OnRewatchCountChangedListener l) {
        mListener = l;
    }

    private void update() {
        mDecrease.setEnabled(mCount >= 1);
        mRewatchCount.setText(mNumberFormat.format(mCount));
    }


    public interface OnRewatchCountChangedListener {
        void onRewatchCountChanged(final ModifyRewatchCountView v);
    }

}
