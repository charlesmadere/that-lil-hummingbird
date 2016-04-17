package com.charlesmadere.hummingbird.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.views.KeyValueTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.OnClick;

public class AnimeDetailsFragment extends BaseFragment {

    private static final String TAG = "AnimeDetailsFragment";
    private static final String KEY_ANIME_V2 = "AnimeV2";

    private AnimeV2 mAnimeV2;

    @Bind(R.id.ibYouTubeLink)
    ImageButton mYouTubeLink;

    @Bind(R.id.kvtvCommunityRating)
    KeyValueTextView mCommunityRating;

    @Bind(R.id.kvtvFinishedAiring)
    KeyValueTextView mFinishedAiring;

    @Bind(R.id.kvtvStartedAiring)
    KeyValueTextView mStartedAiring;

    @Bind(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @Bind(R.id.tvAgeRating)
    TextView mAgeRating;

    @Bind(R.id.tvEpisodeCount)
    TextView mEpisodeCount;

    @Bind(R.id.tvGenres)
    TextView mGenres;

    @Bind(R.id.tvShowType)
    TextView mShowType;

    @Bind(R.id.tvSynopsis)
    TextView mSynopsis;

    @Bind(R.id.tvTitle)
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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPoster.setImageURI(Uri.parse(mAnimeV2.getPosterImage()));
        mTitle.setText(mAnimeV2.getTitle());
        mShowType.setText(mAnimeV2.getShowType().getTextResId());
        mGenres.setText(mAnimeV2.getGenresString(getResources()));
        mAgeRating.setText(mAnimeV2.getAgeRating().getTextResId());

        if (mAnimeV2.getEpisodeCount() > 1) {
            mEpisodeCount.setText(getResources().getQuantityString(R.plurals.x_episodes,
                    mAnimeV2.getEpisodeCount(), NumberFormat.getInstance()
                            .format(mAnimeV2.getEpisodeCount())));
            mEpisodeCount.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.hasSynopsis()) {
            mSynopsis.setText(mAnimeV2.getSynopsis());
            mSynopsis.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.getStartedAiringDate() != null) {
            mStartedAiring.setText(getText(R.string.started_airing),
                    mAnimeV2.getStartedAiringDate().getRelativeDateTimeText(getContext()));
            mStartedAiring.setVisibility(View.VISIBLE);
        }

        if (mAnimeV2.getFinishedAiringDate() != null) {
            mFinishedAiring.setText(getText(R.string.finished_airing),
                    mAnimeV2.getFinishedAiringDate().getRelativeDateTimeText(getContext()));
            mFinishedAiring.setVisibility(View.VISIBLE);
        }

        mCommunityRating.setText(getString(R.string.community_rating_x,
                mAnimeV2.getCommunityRating()));

        if (mAnimeV2.hasYoutubeVideoId()) {
            mYouTubeLink.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ibYouTubeLink)
    void onYouTubeLinkClick() {
        // TODO
    }

}
