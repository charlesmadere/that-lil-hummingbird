package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.MangaActivity;
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

    @BindView(R.id.deleteFeedButton)
    DeleteFeedButton mDeleteFeedButton;

    @BindView(R.id.editFeedButton)
    EditFeedButton mEditFeedButton;

    @BindView(R.id.kvtvChapters)
    KeyValueTextView mChapters;

    @BindView(R.id.kvtvRating)
    KeyValueTextView mRating;

    @BindView(R.id.kvtvVolumes)
    KeyValueTextView mVolumes;

    @BindView(R.id.plusOneFeedButton)
    PlusOneFeedButton mPlusOneFeedButton;

    @BindView(R.id.sdvCover)
    SimpleDraweeView mCover;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvMangaType)
    TextView mMangaType;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.feedButtons)
    View mFeedButtons;

    @BindView(R.id.feedButtonsSpace)
    View mFeedButtonsSpace;


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
        final Context context = getContext();
        context.startActivity(MangaActivity.getLaunchIntent(context, mLibraryEntry.getManga()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final MangaLibraryEntry content) {
        mLibraryEntry = content;
        final Manga manga = mLibraryEntry.getManga();

        mCover.setImageURI(manga.getPosterImage());
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
                    mLibraryEntry.getRating().getValue()));
            mRating.setVisibility(VISIBLE);
        } else {
            mRating.setVisibility(GONE);
        }

        mPlusOneFeedButton.setVisibility(mLibraryEntry.canBeIncremented() ? VISIBLE : INVISIBLE);
    }

    public void setListeners(@Nullable final Listeners l) {
        if (l == null) {
            mFeedButtons.setVisibility(GONE);
            mFeedButtonsSpace.setVisibility(VISIBLE);
            mPlusOneFeedButton.setOnClickListener(null);
            mPlusOneFeedButton.setClickable(false);
            mDeleteFeedButton.setOnClickListener(null);
            mDeleteFeedButton.setClickable(false);
            mEditFeedButton.setOnClickListener(null);
            mEditFeedButton.setClickable(false);
        } else {
            mFeedButtonsSpace.setVisibility(GONE);
            mFeedButtons.setVisibility(VISIBLE);

            mPlusOneFeedButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!l.isLibraryLoading()) {
                        l.onPlusOneClick(MangaLibraryEntryItemView.this);
                    }
                }
            });

            mDeleteFeedButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!l.isLibraryLoading()) {
                        l.onDeleteClick(MangaLibraryEntryItemView.this);
                    }
                }
            });

            mEditFeedButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!l.isLibraryLoading()) {
                        l.onEditClick(MangaLibraryEntryItemView.this);
                    }
                }
            });
        }
    }


    public interface Listeners {
        boolean isLibraryLoading();
        void onDeleteClick(final MangaLibraryEntryItemView v);
        void onEditClick(final MangaLibraryEntryItemView v);
        void onPlusOneClick(final MangaLibraryEntryItemView v);
    }

}
