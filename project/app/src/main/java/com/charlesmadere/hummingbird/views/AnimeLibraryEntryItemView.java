package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Anime;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeLibraryEntryItemView extends CardView implements AdapterView<AnimeLibraryEntry>,
        View.OnClickListener {

    private AnimeLibraryEntry mLibraryEntry;
    private NumberFormat mNumberFormat;

    @BindView(R.id.deleteFeedButton)
    DeleteFeedButton mDeleteFeedButton;

    @BindView(R.id.editFeedButton)
    EditFeedButton mEditFeedButton;

    @BindView(R.id.kvtvProgress)
    KeyValueTextView mProgress;

    @BindView(R.id.kvtvRating)
    KeyValueTextView mRating;

    @BindView(R.id.plusOneFeedButton)
    PlusOneFeedButton mPlusOneFeedButton;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvAnimeType)
    TextView mType;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.feedButtons)
    View mFeedButtons;

    @BindView(R.id.feedButtonsSpace)
    View mFeedButtonsSpace;


    public AnimeLibraryEntryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeLibraryEntryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnimeLibraryEntry getLibraryEntry() {
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
        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AnimeLibraryEntry content) {
        mLibraryEntry = content;
        final Anime anime = mLibraryEntry.getAnime();

        mPoster.setImageURI(anime.getPosterImage());
        mTitle.setText(anime.getTitle());

        if (anime.hasType()) {
            mType.setText(anime.getType().getTextResId());
            mType.setVisibility(VISIBLE);
        } else {
            mType.setVisibility(GONE);
        }

        final Resources res = getResources();

        if (anime.hasGenres()) {
            mGenres.setText(anime.getGenresString(res));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (anime.hasEpisodeCount()) {
            mProgress.setText(R.string.progress, res.getString(R.string.progress_format,
                    mNumberFormat.format(mLibraryEntry.getEpisodesWatched()),
                    mNumberFormat.format(anime.getEpisodeCount())));
        } else {
            mProgress.setText(R.string.progress,
                    mNumberFormat.format(mLibraryEntry.getEpisodesWatched()));
        }

        if (mLibraryEntry.hasRating()) {
            mRating.setText(R.string.rating,
                    mNumberFormat.format(mLibraryEntry.getRating().getValue()));
            mRating.setVisibility(VISIBLE);
        } else {
            mRating.setVisibility(GONE);
        }

        mPlusOneFeedButton.setVisibility(mLibraryEntry.canBeIncremented() ? VISIBLE : GONE);
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
                        l.onPlusOneClick(AnimeLibraryEntryItemView.this);
                    }
                }
            });

            mDeleteFeedButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!l.isLibraryLoading()) {
                        l.onDeleteClick(AnimeLibraryEntryItemView.this);
                    }
                }
            });

            mEditFeedButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!l.isLibraryLoading()) {
                        l.onEditClick(AnimeLibraryEntryItemView.this);
                    }
                }
            });
        }
    }


    public interface Listeners {
        boolean isLibraryLoading();
        void onDeleteClick(final AnimeLibraryEntryItemView v);
        void onEditClick(final AnimeLibraryEntryItemView v);
        void onPlusOneClick(final AnimeLibraryEntryItemView v);
    }

}
