package com.charlesmadere.hummingbird.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GalleryActivity;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeType;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimeDetailsFragment extends BaseFragment {

    private static final String TAG = "AnimeDetailsFragment";
    private static final String KEY_ANIME_DIGEST = "AnimeDigest";

    private AnimeDigest mAnimeDigest;

    @BindView(R.id.cvPoster)
    CardView mPosterContainer;

    @BindView(R.id.llAgeRating)
    LinearLayout mAgeRatingContainer;

    @BindView(R.id.llAired)
    LinearLayout mAiredContainer;

    @BindView(R.id.llAlternateTitle)
    LinearLayout mAlternateTitleContainer;

    @BindView(R.id.llAnimeType)
    LinearLayout mAnimeTypeContainer;

    @BindView(R.id.llCommunityRating)
    LinearLayout mCommunityRatingContainer;

    @BindView(R.id.llEnglishTitle)
    LinearLayout mEnglishTitleContainer;

    @BindView(R.id.llEpisodeCount)
    LinearLayout mEpisodeCountContainer;

    @BindView(R.id.llEpisodeLength)
    LinearLayout mEpisodeLengthContainer;

    @BindView(R.id.llFinishedAiring)
    LinearLayout mFinishedAiringContainer;

    @BindView(R.id.llGenres)
    LinearLayout mGenresContainer;

    @BindView(R.id.llLanguages)
    LinearLayout mLanguagesContainer;

    @BindView(R.id.llProducersContainer)
    LinearLayout mProducersContainer;

    @BindView(R.id.llRomajiTitle)
    LinearLayout mRomajiTitleContainer;

    @BindView(R.id.llStartedAiring)
    LinearLayout mStartedAiringContainer;

    @BindView(R.id.llYouTubeLink)
    LinearLayout mYouTubeLinkContainer;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvAgeRating)
    TextView mAgeRating;

    @BindView(R.id.tvAgeRatingGuide)
    TextView mAgeRatingGuide;

    @BindView(R.id.tvAired)
    TextView mAired;

    @BindView(R.id.tvAlternateTitle)
    TextView mAlternateTitle;

    @BindView(R.id.tvAnimeType)
    TextView mAnimeType;

    @BindView(R.id.tvCanonicalTitle)
    TextView mCanonicalTitle;

    @BindView(R.id.tvCommunityRating)
    TextView mCommunityRating;

    @BindView(R.id.tvEnglishTitle)
    TextView mEnglishTitle;

    @BindView(R.id.tvEpisodeCountBody)
    TextView mEpisodeCountBody;

    @BindView(R.id.tvEpisodeCountHeader)
    TextView mEpisodeCountHeader;

    @BindView(R.id.tvEpisodeLengthBody)
    TextView mEpisodeLengthBody;

    @BindView(R.id.tvEpisodeLengthHeader)
    TextView mEpisodeLengthHeader;

    @BindView(R.id.tvFinishedAiring)
    TextView mFinishedAiring;

    @BindView(R.id.tvGenresBody)
    TextView mGenresBody;

    @BindView(R.id.tvGenresHeader)
    TextView mGenresHeader;

    @BindView(R.id.tvLanguagesBody)
    TextView mLanguagesBody;

    @BindView(R.id.tvLanguagesHeader)
    TextView mLanguagesHeader;

    @BindView(R.id.tvProducersBody)
    TextView mProducersBody;

    @BindView(R.id.tvProducersHeader)
    TextView mProducersHeader;

    @BindView(R.id.tvRomajiTitle)
    TextView mRomajiTitle;

    @BindView(R.id.tvStartedAiring)
    TextView mStartedAiring;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvYouTubeLink)
    TextView mYouTubeLinkText;


    public static AnimeDetailsFragment create(final AnimeDigest digest) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_ANIME_DIGEST, digest);

        final AnimeDetailsFragment fragment = new AnimeDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mAnimeDigest = args.getParcelable(KEY_ANIME_DIGEST);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_details, container, false);
    }

    @OnClick(R.id.cvPoster)
    void onPosterClick() {
        startActivity(GalleryActivity.getLaunchIntent(getContext(), mAnimeDigest.getInfo(),
                mAnimeDigest.getInfo().getPosterImage()));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final AnimeDigest.Info info = mAnimeDigest.getInfo();
        final Context context = getContext();
        final Resources resources = getResources();
        final NumberFormat numberFormat = NumberFormat.getInstance();

        if (info.hasPosterImage()) {
            mPoster.setImageURI(Uri.parse(info.getPosterImage()));
            mPosterContainer.setVisibility(View.VISIBLE);
        }

        mCanonicalTitle.setText(info.getCanonicalTitle());

        if (info.hasAlternateTitle()) {
            mAlternateTitle.setText(info.getAlternateTitle());
            mAlternateTitleContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasEnglishTitle()) {
            mEnglishTitle.setText(info.getEnglishTitle());
            mEnglishTitleContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasRomajiTitle()) {
            mRomajiTitle.setText(info.getRomajiTitle());
            mRomajiTitleContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasType()) {
            mAnimeType.setText(info.getType().getTextResId());
            mAnimeTypeContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasAgeRating()) {
            mAgeRating.setText(info.getAgeRating().getTextResId());
            mAgeRatingContainer.setVisibility(View.VISIBLE);

            if (info.hasAgeRatingGuide()) {
                mAgeRatingGuide.setText(info.getAgeRatingGuide());
            } else {
                mAgeRatingGuide.setText(R.string.age_rating);
            }
        }

        if (info.hasGenres()) {
            mGenresHeader.setText(info.getGenresString(resources));
            mGenresBody.setText(resources.getQuantityText(R.plurals.genres,
                    info.getGenres().size()));
            mGenresContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasLanguages()) {
            mLanguagesHeader.setText(info.getLanguagesString(resources));
            mLanguagesBody.setText(resources.getQuantityText(R.plurals.languages,
                    info.getLanguages().size()));
            mLanguagesContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasEpisodeCount() && info.getType() != AnimeType.MOVIE) {
            mEpisodeCountHeader.setText(numberFormat.format(info.getEpisodeCount()));
            mEpisodeCountBody.setText(resources.getQuantityText(R.plurals.episodes,
                    info.getEpisodeCount()));
            mEpisodeCountContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasEpisodeLength()) {
            mEpisodeLengthHeader.setText(resources.getQuantityString(R.plurals.x_minutes,
                    info.getEpisodeLength(), numberFormat.format(info.getEpisodeLength())));

            if (info.getType() == AnimeType.MOVIE) {
                mEpisodeLengthBody.setText(R.string.length);
            } else {
                mEpisodeLengthBody.setText(R.string.episode_length);
            }

            mEpisodeLengthContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasStartedAiringDate()) {
            if (info.getType() == AnimeType.MOVIE) {
                mAired.setText(info.getStartedAiringDate().getRelativeTimeText(context));
                mAiredContainer.setVisibility(View.VISIBLE);
            } else {
                mStartedAiring.setText(info.getStartedAiringDate().getRelativeTimeText(context));
                mStartedAiringContainer.setVisibility(View.VISIBLE);

                if (info.hasFinishedAiringDate()) {
                    mFinishedAiring.setText(info.getFinishedAiringDate().getRelativeTimeText(context));
                    mFinishedAiringContainer.setVisibility(View.VISIBLE);
                }
            }
        }

        if (info.hasBayesianRating()) {
            mCommunityRating.setText(String.format(Locale.getDefault(), "%.4f",
                    info.getBayesianRating()));
            mCommunityRatingContainer.setVisibility(View.VISIBLE);
        }

        if (mAnimeDigest.hasProducers()) {
            mProducersHeader.setText(mAnimeDigest.getProducersString(resources));
            mProducersBody.setText(resources.getQuantityText(R.plurals.producers,
                    mAnimeDigest.getProducers().size()));
            mProducersContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasYouTubeVideoId()) {
            mYouTubeLinkText.setText(info.getYouTubeVideoUrl());
            mYouTubeLinkContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasSynopsis()) {
            mSynopsis.setText(info.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

    @OnClick(R.id.llYouTubeLink)
    void onYouTubeLinkClick() {
        MiscUtils.openUrl(getActivity(), mAnimeDigest.getInfo().getYouTubeVideoUrl());
    }

}
