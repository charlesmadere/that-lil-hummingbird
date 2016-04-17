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
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.OnClick;

public class AnimeDetailsFragment extends BaseFragment {

    private static final String TAG = "AnimeDetailsFragment";
    private static final String KEY_ANIME_V2 = "AnimeV2";

    private AnimeV2 mAnimeV2;

    @Bind(R.id.ibYouTubeLink)
    ImageButton mYouTubeLink;

    @Bind(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @Bind(R.id.tvEpisodeCount)
    TextView mEpisodeCount;

    @Bind(R.id.tvFinishedAiring)
    TextView mFinishedAiring;

    @Bind(R.id.tvGenres)
    TextView mGenres;

    @Bind(R.id.tvShowType)
    TextView mShowType;

    @Bind(R.id.tvStartedAiring)
    TextView mStartedAiring;

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

        if (mAnimeV2.hasYoutubeVideoId()) {
            mYouTubeLink.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ibYouTubeLink)
    void onYouTubeLinkClick() {
        // TODO
    }

}
