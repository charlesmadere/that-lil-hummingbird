package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Manga;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MangaLibraryEntryItemView extends CardView implements AdapterView<MangaLibraryEntry>,
        View.OnClickListener {

    private MangaLibraryEntry mLibraryEntry;
    private NumberFormat mNumberFormat;
    private OnEditClickListener mListener;

    @BindView(R.id.ibEdit)
    ImageButton mEdit;

    @BindView(R.id.kvtvChapters)
    KeyValueTextView mChapters;

    @BindView(R.id.kvtvRating)
    KeyValueTextView mRating;

    @BindView(R.id.kvtvVolumes)
    KeyValueTextView mVolumes;

    @BindView(R.id.sdvCover)
    SimpleDraweeView mCover;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvMangaType)
    TextView mMangaType;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public MangaLibraryEntryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MangaLibraryEntryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MangaLibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    @Override
    public void onClick(final View view) {
        mListener.onEditClick(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final MangaLibraryEntry content) {
        mLibraryEntry = content;
        final Manga manga = mLibraryEntry.getManga();

        mCover.setImageURI(manga.getCoverImage());
        mTitle.setText(manga.getTitle());

        if (manga.hasType()) {
            mMangaType.setText(manga.getType().getTextResId());
            mMangaType.setVisibility(VISIBLE);
        } else {
            mMangaType.setVisibility(GONE);
        }

        final Resources res = getResources();

        if (manga.hasGenres()) {
            mGenres.setText(manga.getGenresString(res));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (manga.hasSynopsis()) {
            mSynopsis.setText(manga.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }

        if (manga.hasChapterCount()) {
            mChapters.setText(R.string.chapters, res.getString(R.string.progress_format,
                    mNumberFormat.format(mLibraryEntry.getChaptersRead()),
                    mNumberFormat.format(manga.getChapterCount())));
        } else {
            mChapters.setText(R.string.chapters, mNumberFormat.format(
                    mLibraryEntry.getChaptersRead()));
        }

        if (manga.hasVolumeCount()) {
            mVolumes.setText(R.string.volumes, res.getString(R.string.progress_format,
                    mNumberFormat.format(mLibraryEntry.getVolumesRead()),
                    mNumberFormat.format(manga.getVolumeCount())));
        } else {
            mVolumes.setText(R.string.volumes, mNumberFormat.format(
                    mLibraryEntry.getVolumesRead()));
        }

        if (mLibraryEntry.hasRating()) {
            mRating.setText(R.string.rating, mNumberFormat.format(
                    mLibraryEntry.getRating().mValue));
            mRating.setVisibility(VISIBLE);
        } else {
            mRating.setVisibility(GONE);
        }

        mEdit.setVisibility(mListener == null ? GONE : VISIBLE);
    }

    public void setOnEditClickListener(@Nullable final OnEditClickListener l) {
        mListener = l;
        mEdit.setVisibility(mListener == null ? GONE : VISIBLE);
    }


    public interface OnEditClickListener {
        void onEditClick(final MangaLibraryEntryItemView v);
    }

}
