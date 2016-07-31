package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GalleryActivity;
import com.charlesmadere.hummingbird.misc.AnimeDigestProvider;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeType;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimeDetailsFragment extends BaseFragment {

    private static final String TAG = "AnimeDetailsFragment";

    private AnimeDigestProvider mProvider;

    @BindView(R.id.cvPoster)
    CardView mPosterContainer;

    @BindView(R.id.hbivAgeRating)
    HeadBodyItemView mAgeRating;

    @BindView(R.id.hbivAired)
    HeadBodyItemView mAired;

    @BindView(R.id.hbivAlternateTitle)
    HeadBodyItemView mAlternateTitle;

    @BindView(R.id.hbivAnimeType)
    HeadBodyItemView mAnimeType;

    @BindView(R.id.hbivCanonicalTitle)
    HeadBodyItemView mCanonicalTitle;

    @BindView(R.id.hbivCommunityRating)
    HeadBodyItemView mCommunityRating;

    @BindView(R.id.hbivEnglishTitle)
    HeadBodyItemView mEnglishTitle;

    @BindView(R.id.hbivEpisodeCount)
    HeadBodyItemView mEpisodeCount;

    @BindView(R.id.hbivEpisodeLength)
    HeadBodyItemView mEpisodeLength;

    @BindView(R.id.hbivFinishedAiring)
    HeadBodyItemView mFinishedAiring;

    @BindView(R.id.hbivGenres)
    HeadBodyItemView mGenres;

    @BindView(R.id.hbivLanguages)
    HeadBodyItemView mLanguages;

    @BindView(R.id.hbivProducers)
    HeadBodyItemView mProducers;

    @BindView(R.id.hbivRomajiTitle)
    HeadBodyItemView mRomajiTitle;

    @BindView(R.id.hbivStartedAiring)
    HeadBodyItemView mStartedAiring;

    @BindView(R.id.hbivWillAir)
    HeadBodyItemView mWillAir;

    @BindView(R.id.hbivYouTubeLink)
    HeadBodyItemView mYouTubeLink;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;


    public static AnimeDetailsFragment create() {
        return new AnimeDetailsFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof AnimeDigestProvider) {
            mProvider = (AnimeDigestProvider) fragment;
        } else {
            final Activity activity = MiscUtils.getActivity(context);

            if (activity instanceof AnimeDigestProvider) {
                mProvider = (AnimeDigestProvider) activity;
            }
        }

        if (mProvider == null) {
            throw new IllegalStateException(TAG + " must have a Listener");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_details, container, false);
    }

    @OnClick(R.id.cvPoster)
    void onPosterClick() {
        final AnimeDigest animeDigest = mProvider.getAnimeDigest();
        startActivity(GalleryActivity.getLaunchIntent(getContext(), animeDigest.getInfo(),
                animeDigest.getInfo().getPosterImage()));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final AnimeDigest animeDigest = mProvider.getAnimeDigest();
        final AnimeDigest.Info info = animeDigest.getInfo();
        final Context context = getContext();
        final Resources resources = getResources();
        final NumberFormat numberFormat = NumberFormat.getInstance();

        if (info.hasPosterImage()) {
            mPoster.setImageURI(info.getPosterImage());
            mPosterContainer.setVisibility(View.VISIBLE);
        }

        mCanonicalTitle.setHead(info.getCanonicalTitle());

        if (info.hasAlternateTitle()) {
            mAlternateTitle.setHead(info.getAlternateTitle());
            mAlternateTitle.setVisibility(View.VISIBLE);
        }

        if (info.hasEnglishTitle()) {
            mEnglishTitle.setHead(info.getEnglishTitle());
            mEnglishTitle.setVisibility(View.VISIBLE);
        }

        if (info.hasRomajiTitle()) {
            mRomajiTitle.setHead(info.getRomajiTitle());
            mRomajiTitle.setVisibility(View.VISIBLE);
        }

        if (info.hasType()) {
            mAnimeType.setHead(info.getType().getTextResId());
            mAnimeType.setVisibility(View.VISIBLE);
        }

        if (info.hasAgeRating()) {
            mAgeRating.setHead(info.getAgeRating().getTextResId());

            if (info.hasAgeRatingGuide()) {
                mAgeRating.setBody(info.getAgeRatingGuide());
            }

            mAgeRating.setVisibility(View.VISIBLE);
        }

        if (info.hasGenres()) {
            mGenres.setHead(info.getGenresString(resources));
            mGenres.setBody(resources.getQuantityText(R.plurals.genres, info.getGenresSize()));
            mGenres.setVisibility(View.VISIBLE);
        }

        if (info.hasLanguages()) {
            mLanguages.setHead(info.getLanguagesString(resources));
            mLanguages.setBody(resources.getQuantityText(R.plurals.languages,
                    info.getLanguagesSize()));
            mLanguages.setVisibility(View.VISIBLE);
        }

        if (info.hasEpisodeCount() && info.getType() != AnimeType.MOVIE) {
            mEpisodeCount.setHead(numberFormat.format(info.getEpisodeCount()));
            mEpisodeCount.setBody(resources.getQuantityText(R.plurals.episodes,
                    info.getEpisodeCount()));
            mEpisodeCount.setVisibility(View.VISIBLE);
        }

        if (info.hasEpisodeLength()) {
            mEpisodeLength.setHead(resources.getQuantityString(R.plurals.x_minutes,
                    info.getEpisodeLength(), numberFormat.format(info.getEpisodeLength())));

            if (info.getType() == AnimeType.MOVIE) {
                mEpisodeLength.setBody(R.string.length);
            } else {
                mEpisodeLength.setBody(R.string.episode_length);
            }

            mEpisodeLength.setVisibility(View.VISIBLE);
        }

        if (info.hasStartedAiringDate()) {
            if (info.getType() == AnimeType.MOVIE) {
                mAired.setHead(info.getStartedAiringDate().getRelativeTimeText(context));
                mAired.setVisibility(View.VISIBLE);
            } else if (info.getStartedAiringDate().isInTheFuture()) {
                mWillAir.setHead(info.getStartedAiringDate().getRelativeTimeText(context));
                mWillAir.setVisibility(View.VISIBLE);
            } else {
                mStartedAiring.setHead(info.getStartedAiringDate().getRelativeTimeText(context));
                mStartedAiring.setVisibility(View.VISIBLE);

                if (info.hasFinishedAiringDate()) {
                    mFinishedAiring.setHead(info.getFinishedAiringDate().getRelativeTimeText(context));
                    mFinishedAiring.setVisibility(View.VISIBLE);
                }
            }
        }

        if (info.hasBayesianRating()) {
            mCommunityRating.setHead(String.format(Locale.getDefault(), "%.4f",
                    info.getBayesianRating()));
            mCommunityRating.setVisibility(View.VISIBLE);
        }

        if (animeDigest.hasProducers()) {
            mProducers.setHead(animeDigest.getProducersString(resources));
            mProducers.setBody(resources.getQuantityText(R.plurals.producers,
                    animeDigest.getProducersSize()));
            mProducers.setVisibility(View.VISIBLE);
        }

        if (info.hasYouTubeVideoId()) {
            mYouTubeLink.setBody(info.getYouTubeVideoUrl());
            mYouTubeLink.setVisibility(View.VISIBLE);
        }

        if (info.hasSynopsis()) {
            mSynopsis.setText(info.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

    @OnClick(R.id.hbivYouTubeLink)
    void onYouTubeLinkClick() {
        MiscUtils.openUrl(getActivity(), mProvider.getAnimeDigest().getInfo().getYouTubeVideoUrl());
    }

}
