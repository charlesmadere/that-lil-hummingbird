package com.charlesmadere.hummingbird.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GalleryActivity;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.SimpleDate;
import com.charlesmadere.hummingbird.views.KeyValueTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimeDetailsFragment extends BaseFragment {

    private static final String TAG = "AnimeDetailsFragment";
    private static final String KEY_ANIME_V2 = "AnimeV2";

    private AnimeV2 mAnimeV2;

    @BindView(R.id.ibYouTubeLink)
    ImageButton mYouTubeLink;

    @BindView(R.id.kvtvCommunityRating)
    KeyValueTextView mCommunityRating;

    @BindView(R.id.kvtvAired)
    KeyValueTextView mAired;

    @BindView(R.id.kvtvFinishedAiring)
    KeyValueTextView mFinishedAiring;

    @BindView(R.id.kvtvProducers)
    KeyValueTextView mProducers;

    @BindView(R.id.kvtvStartedAiring)
    KeyValueTextView mStartedAiring;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvAgeRating)
    TextView mAgeRating;

    @BindView(R.id.tvEpisodeCount)
    TextView mEpisodeCount;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvShowType)
    TextView mShowType;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public static AnimeDetailsFragment create(final AnimeV2 animeV2) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_ANIME_V2, animeV2);

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
        mAnimeV2 = args.getParcelable(KEY_ANIME_V2);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_details, container, false);
    }

    @OnClick(R.id.cvPoster)
    void onPosterClick() {
        startActivity(GalleryActivity.getLaunchIntent(getContext(), mAnimeV2,
                mAnimeV2.getPosterImage()));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPoster.setImageURI(Uri.parse(mAnimeV2.getPosterImage()));
        mTitle.setText(mAnimeV2.getTitle());
        mShowType.setText(mAnimeV2.getShowType().getTextResId());

        if (mAnimeV2.hasGenres()) {
            mGenres.setText(mAnimeV2.getGenresString(getResources()));
            mGenres.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.hasAgeRating()) {
            mAgeRating.setText(mAnimeV2.getAgeRating().getTextResId());
            mAgeRating.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.getShowType() != AbsAnime.ShowType.MOVIE && mAnimeV2.hasEpisodeCount()) {
            mEpisodeCount.setText(getResources().getQuantityString(R.plurals.x_episodes,
                    mAnimeV2.getEpisodeCount(), NumberFormat.getInstance()
                            .format(mAnimeV2.getEpisodeCount())));
            mEpisodeCount.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.hasStartedAiringDate()) {
            if (mAnimeV2.getShowType() == AbsAnime.ShowType.MOVIE) {
                setAiringDateView(mAired, R.string.aired, mAnimeV2.getStartedAiringDate());
            } else {
                setAiringDateView(mStartedAiring, R.string.started_airing,
                        mAnimeV2.getStartedAiringDate());

                if (mAnimeV2.hasFinishedAiringDate()) {
                    setAiringDateView(mFinishedAiring, R.string.finished_airing,
                            mAnimeV2.getFinishedAiringDate());
                }
            }
        }

        if (mAnimeV2.hasFinishedAiringDate()) {
            mFinishedAiring.setText(getText(R.string.finished_airing),
                    mAnimeV2.getFinishedAiringDate().getRelativeTimeText(getContext()));
            mFinishedAiring.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.hasProducers()) {
            mProducers.setText(getResources().getQuantityText(R.plurals.producers,
                    mAnimeV2.getProducers().size()), mAnimeV2.getProducersString(getResources()));
            mProducers.setVisibility(View.VISIBLE);
        }

        mCommunityRating.setText(getText(R.string.community_rating),
                String.format(Locale.getDefault(), "%.4f", mAnimeV2.getCommunityRating()));

        if (mAnimeV2.hasYoutubeVideoId()) {
            mYouTubeLink.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.hasSynopsis()) {
            mSynopsis.setText(mAnimeV2.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

    @OnClick(R.id.ibYouTubeLink)
    void onYouTubeLinkClick() {
        openUrl(Constants.YOUTUBE_BASE_URL + mAnimeV2.getYoutubeVideoId());
    }

    private void setAiringDateView(final KeyValueTextView view, @StringRes final int keyTextResId,
            final SimpleDate date) {
        view.setText(getText(keyTextResId), date.getRelativeTimeText(getContext()));
        view.setVisibility(View.VISIBLE);
    }

}
