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
        mTitle.setText(R.string.chapters_read);

        if (digest.getInfo().hasChapterCount()) {
            setContent(libraryUpdate.getChaptersRead(), digest.getInfo().getChapterCount());
        } else {
            setContent(libraryUpdate.getChaptersRead());
        }
    }

    public void setForChaptersRead(final MangaLibraryUpdate libraryUpdate,
            final MangaLibraryEntry libraryEntry) {
        mTitle.setText(R.string.chapters_read);

        if (libraryEntry.getManga().hasChapterCount()) {
            setContent(libraryUpdate.getChaptersRead(), libraryEntry.getManga().getChapterCount());
        } else {
            setContent(libraryUpdate.getChaptersRead());
        }
    }

    public void setForReReadCount(final MangaLibraryUpdate libraryUpdate) {
        mTitle.setText(R.string.reread_times);
        setContent(libraryUpdate.getReReadCount());
    }

    public void setForRewatchedTimes(final AnimeLibraryUpdate libraryUpdate) {
        mTitle.setText(R.string.rewatched_times);
        setContent(libraryUpdate.getRewatchCount());
    }

    public void setForVolumesRead(final MangaLibraryUpdate libraryUpdate,
            final MangaDigest digest) {
        mTitle.setText(R.string.volumes_read);

        if (digest.getInfo().hasVolumeCount()) {
            setContent(libraryUpdate.getVolumesRead(), digest.getInfo().getVolumeCount());
        } else {
            setContent(libraryUpdate.getVolumesRead());
        }
    }

    public void setForVolumesRead(final MangaLibraryUpdate libraryUpdate,
            final MangaLibraryEntry libraryEntry) {
        mTitle.setText(R.string.volumes_read);

        if (libraryEntry.getManga().hasVolumeCount()) {
            setContent(libraryUpdate.getVolumesRead(), libraryEntry.getManga().getVolumeCount());
        } else {
            setContent(libraryUpdate.getVolumesRead());
        }
    }

    public void setForWatchedCount(final AnimeLibraryUpdate libraryUpdate,
            final AnimeDigest digest) {
        mTitle.setText(R.string.watched);

        if (digest.getInfo().hasEpisodeCount()) {
            setContent(libraryUpdate.getEpisodesWatched(), digest.getInfo().getEpisodeCount());
        } else {
            setContent(libraryUpdate.getEpisodesWatched());
        }
    }

    public void setForWatchedCount(final AnimeLibraryUpdate libraryUpdate,
            final AnimeLibraryEntry libraryEntry) {
        mTitle.setText(R.string.watched);

        if (libraryEntry.getAnime().hasEpisodeCount()) {
            setContent(libraryUpdate.getEpisodesWatched(), libraryEntry.getAnime().getEpisodeCount());
        } else {
            setContent(libraryUpdate.getEpisodesWatched());
        }
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
