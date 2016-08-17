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
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyNumberView extends LinearLayout {

    private int mNumber;
    private Integer mMax;
    private Listener mListener;
    private NumberFormat mNumberFormat;

    @BindView(R.id.ibDecrease)
    ImageButton mDecrease;

    @BindView(R.id.ibIncrease)
    ImageButton mIncrease;

    @BindView(R.id.tvCount)
    TextView mCount;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public ModifyNumberView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyNumberView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ModifyNumberView(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getNumber() {
        return mNumber;
    }

    private void notifyListener() {
        if (mListener != null) {
            mListener.onNumberChanged(this);
        }
    }

    @OnClick(R.id.ibDecrease)
    void onDecreaseClick() {
        if (mNumber < 1) {
            return;
        }

        --mNumber;
        update();
        notifyListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @OnClick(R.id.ibIncrease)
    void onIncreaseClick() {
        if (mMax != null && mNumber == mMax) {
            return;
        }

        ++mNumber;
        update();
        notifyListener();
    }

    public void setContent(final int number) {
        setContent(number, null);
    }

    public void setContent(int number, @Nullable final Integer max) {
        if (number < 0) {
            number = 0;
        }

        mNumber = number;

        if (max != null) {
            if (max < mNumber) {
                throw new IllegalArgumentException("max (" + max + ") is less than number ("
                        + number + ")");
            } else {
                mMax = max;
            }
        }

        update();
    }

    public void setForChaptersRead(final MangaLibraryUpdate libraryUpdate,
            final MangaDigest digest) {
        setContent(libraryUpdate.getChaptersRead(), digest.getInfo().getChapterCount());
        mTitle.setText(R.string.chapters_read);
    }

    public void setForChaptersRead(final MangaLibraryUpdate libraryUpdate,
            final MangaLibraryEntry libraryEntry) {
        setContent(libraryUpdate.getChaptersRead(), libraryEntry.getManga().getChapterCount());
        mTitle.setText(R.string.chapters_read);
    }

    public void setForReReadCount(final MangaLibraryUpdate libraryUpdate) {
        setContent(libraryUpdate.getReReadCount());
        mTitle.setText(R.string.reread_times);
    }

    public void setForRewatchedTimes(final AnimeLibraryUpdate libraryUpdate) {
        setContent(libraryUpdate.getRewatchCount());
        mTitle.setText(R.string.rewatched_times);
    }

    public void setForVolumesRead(final MangaLibraryUpdate libraryUpdate,
            final MangaDigest digest) {
        setContent(libraryUpdate.getVolumesRead(), digest.getInfo().getVolumeCount());
        mTitle.setText(R.string.volumes_read);
    }

    public void setForVolumesRead(final MangaLibraryUpdate libraryUpdate,
            final MangaLibraryEntry libraryEntry) {
        setContent(libraryUpdate.getVolumesRead(), libraryEntry.getManga().getVolumeCount());
        mTitle.setText(R.string.volumes_read);
    }

    public void setForWatchedCount(final AnimeLibraryUpdate libraryUpdate,
            final AnimeDigest digest) {
        setContent(libraryUpdate.getEpisodesWatched(), digest.getInfo().getEpisodeCount());
        mTitle.setText(R.string.watched);
    }

    public void setForWatchedCount(final AnimeLibraryUpdate libraryUpdate,
            final AnimeLibraryEntry libraryEntry) {
        setContent(libraryUpdate.getEpisodesWatched(), libraryEntry.getAnime().getEpisodeCount());
        mTitle.setText(R.string.watched);
    }

    public void setListener(@Nullable final Listener listener) {
        mListener = listener;
    }

    private void update() {
        mDecrease.setEnabled(mNumber >= 1);

        if (mMax == null) {
            mCount.setText(mNumberFormat.format(mNumber));
            mIncrease.setEnabled(true);
        } else {
            mCount.setText(getResources().getString(R.string.progress_format,
                    mNumberFormat.format(mNumber), mNumberFormat.format(mMax)));
            mIncrease.setEnabled(mNumber < mMax);
        }
    }


    public interface Listener {
        void onNumberChanged(final ModifyNumberView v);
    }

}
