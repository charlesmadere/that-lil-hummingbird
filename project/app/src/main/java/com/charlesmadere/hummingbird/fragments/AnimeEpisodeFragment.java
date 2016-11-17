package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimeEpisodeFragment extends BaseBottomSheetDialogFragment {

    private static final String TAG = "AnimeEpisodeFragment";
    private static final String KEY_EPISODE = "Episode";

    private AnimeDigest.Episode mEpisode;

    @Nullable
    @BindView(R.id.sdvThumbnail)
    SimpleDraweeView mThumbnail;

    @BindView(R.id.tvNumber)
    TextView mNumber;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public static AnimeEpisodeFragment create(final AnimeDigest.Episode episode) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_EPISODE, episode);

        final AnimeEpisodeFragment fragment = new AnimeEpisodeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTitle.setText(mEpisode.getTitle());
        mNumber.setText(NumberFormat.getInstance().format(mEpisode.getNumber()));

        if (mEpisode.hasThumbnail()) {
            // noinspection ConstantConditions
            mThumbnail.setImageURI(mEpisode.getThumbnail());
        }

        if (mEpisode.hasSynopsis()) {
            mSynopsis.setText(mEpisode.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mEpisode = args.getParcelable(KEY_EPISODE);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final int layoutResId = mEpisode.hasThumbnail() ?
                R.layout.fragment_anime_episode_with_thumbnail :
                R.layout.fragment_anime_episode_without_thumbnail;

        return inflater.inflate(layoutResId, container, false);
    }

}
