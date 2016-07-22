package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InternalAnimeItemView extends LinearLayout {

    private AbsAnime mAnime;
    private AnimeLibraryEntry mLibraryEntry;
    private NumberFormat mNumberFormat;
    private OnEditClickListener mListener;

    @BindView(R.id.ibEdit)
    ImageButton mEdit;

    @BindView(R.id.kvtvProgress)
    KeyValueTextView mProgress;

    @BindView(R.id.kvtvRating)
    KeyValueTextView mRating;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvAnimeType)
    TextView mAnimeType;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public InternalAnimeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public InternalAnimeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InternalAnimeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AbsAnime getAnime() {
        return mAnime;
    }

    public AnimeLibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    @OnClick(R.id.ibEdit)
    void onEditClick() {
        mListener.onEditClick(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setContent(final AbsAnime content) {
        mAnime = content;
        mLibraryEntry = null;

        mPoster.setImageURI(mAnime.getImage());
        mTitle.setText(mAnime.getTitle());

        if (mAnime.hasType()) {
            mAnimeType.setText(mAnime.getType().getTextResId());
            mAnimeType.setVisibility(VISIBLE);
        } else {
            mAnimeType.setVisibility(GONE);
        }

        if (mAnime.hasGenres()) {
            mGenres.setText(mAnime.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (mAnime.hasSynopsis()) {
            mSynopsis.setText(mAnime.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

    public void setContent(final AnimeLibraryEntry content) {
        setContent(content.getAnime());
        mLibraryEntry = content;

        final Resources res = getResources();

        if (mAnime.hasEpisodeCount()) {
            mProgress.setText(R.string.progress, res.getString(R.string.progress_format,
                    mNumberFormat.format(mLibraryEntry.getEpisodesWatched()),
                    mNumberFormat.format(mAnime.getEpisodeCount())));
        } else {
            mProgress.setText(R.string.progress, mNumberFormat.format(
                    mLibraryEntry.getEpisodesWatched()));
        }

        mProgress.setVisibility(VISIBLE);

        if (mLibraryEntry.hasRating()) {
            mRating.setText(R.string.rating,
                    mNumberFormat.format(mLibraryEntry.getRating().getValue()));
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
        void onEditClick(final InternalAnimeItemView v);
    }

}
