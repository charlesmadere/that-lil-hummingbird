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
import com.charlesmadere.hummingbird.models.LibraryUpdate;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyWatchCountView extends LinearLayout {

    private int mCount;
    private Integer mMax;
    private NumberFormat mNumberFormat;
    private OnWatchCountChangedListener mListener;

    @BindView(R.id.ibDecreaseCount)
    ImageButton mDecrease;

    @BindView(R.id.ibIncreaseCount)
    ImageButton mIncrease;

    @BindView(R.id.tvWatchCount)
    TextView mWatchCount;


    public ModifyWatchCountView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyWatchCountView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ModifyWatchCountView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getCount() {
        return mCount;
    }

    private void notifyListener() {
        if (mListener != null) {
            mListener.onWatchCountChanged(this);
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
        if (mMax != null && mCount == mMax) {
            return;
        }

        ++mCount;
        update();
        notifyListener();
    }

    public void setContent(final LibraryUpdate libraryUpdate) {
        setCountAndMax(libraryUpdate.getEpisodesWatched(), libraryUpdate.getLibraryEntry()
                .getAnime().getEpisodeCount());
    }

    public void setCountAndMax(int count, @Nullable Integer max) {
        if (count < 0) {
            count = 0;
        }
        mCount = count;

        if (max != null && max < count) {
            max = count;
        }
        mMax = max;

        update();
    }

    public void setOnWatchCountChangedListener(@Nullable final OnWatchCountChangedListener l) {
        mListener = l;
    }

    private void update() {
        mDecrease.setEnabled(mCount >= 1);

        if (mMax == null) {
            mWatchCount.setText(mNumberFormat.format(mCount));
            mIncrease.setEnabled(true);
        } else {
            mWatchCount.setText(getResources().getString(R.string.progress_format,
                    mNumberFormat.format(mCount), mNumberFormat.format(mMax)));
            mIncrease.setEnabled(mCount < mMax);
        }
    }


    public interface OnWatchCountChangedListener {
        void onWatchCountChanged(final ModifyWatchCountView v);
    }

}
