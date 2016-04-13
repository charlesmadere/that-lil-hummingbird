package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LibraryEntryItemView extends CardView implements AdapterView<LibraryEntry>,
        View.OnClickListener {

    @Bind(R.id.kvtvProgress)
    KeyValueTextView mProgress;

    @Bind(R.id.kvtvRating)
    KeyValueTextView mRating;

    @Bind(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @Bind(R.id.tvGenres)
    TextView mGenres;

    @Bind(R.id.tvShowType)
    TextView mShowType;

    @Bind(R.id.tvSynopsis)
    TextView mSynopsis;

    @Bind(R.id.tvTitle)
    TextView mTitle;

    private LibraryEntry mLibraryEntry;
    private NumberFormat mNumberFormat;


    public LibraryEntryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LibraryEntryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context, mLibraryEntry.getAnime()));
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
    public void setContent(final LibraryEntry content) {
        mLibraryEntry = content;

        final AbsAnime anime = mLibraryEntry.getAnime();
        mPoster.setImageURI(Uri.parse(anime.getCoverImage()));
        mTitle.setText(anime.getTitle());
        mShowType.setText(anime.getShowType().getTextResId());

        if (anime.hasGenres()) {
            mGenres.setText(anime.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (anime.hasSynopsis()) {
            mSynopsis.setText(anime.getSynopsis());
            mSynopsis.setVisibility(VISIBLE);
        } else {
            mSynopsis.setVisibility(GONE);
        }

        final Resources res = getResources();
        mProgress.setText(res.getText(R.string.progress), anime.getEpisodeCount() == 0 ?
                String.valueOf(mLibraryEntry.getEpisodesWatched()) : res.getString(
                R.string.progress_format, mNumberFormat.format(mLibraryEntry.getEpisodesWatched()),
                mNumberFormat.format(anime.getEpisodeCount())));

        if (mLibraryEntry.hasRating()) {
            mRating.setText(res.getText(R.string.rating), mLibraryEntry.getRating().getValue());
            mRating.setVisibility(VISIBLE);
        } else {
            mRating.setVisibility(GONE);
        }
    }

}
