package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InternalAnimeItemView extends LinearLayout {

    private NumberFormat mNumberFormat;

    @BindView(R.id.kvtvProgress)
    KeyValueTextView mProgress;

    @BindView(R.id.kvtvRating)
    KeyValueTextView mRating;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvShowType)
    TextView mShowType;

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
        mPoster.setImageURI(Uri.parse(content.getCoverImage()));
        mTitle.setText(content.getTitle());
        mShowType.setText(content.getShowType().getTextResId());

        if (content.hasGenres()) {
            mGenres.setText(content.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (content.hasSynopsis()) {
            mSynopsis.setText(content.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

    public void setContent(final LibraryEntry content) {
        final AbsAnime anime = content.getAnime();
        setContent(anime);

        final Resources res = getResources();
        mProgress.setText(res.getText(R.string.progress), anime.hasEpisodeCount() &&
                anime.getEpisodeCount() >= 1 ? res.getString(R.string.progress_format,
                mNumberFormat.format(content.getEpisodesWatched()), mNumberFormat.format(
                        anime.getEpisodeCount())) : mNumberFormat.format(content.getEpisodesWatched()));
        mProgress.setVisibility(VISIBLE);

        if (content.hasRating()) {
            mRating.setText(res.getText(R.string.rating), content.getRating().getValue());
            mRating.setVisibility(VISIBLE);
        } else {
            mRating.setVisibility(GONE);
        }
    }

}
