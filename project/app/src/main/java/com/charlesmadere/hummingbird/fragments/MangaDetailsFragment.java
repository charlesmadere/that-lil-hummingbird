package com.charlesmadere.hummingbird.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;

public class MangaDetailsFragment extends BaseMangaFragment {

    private static final String TAG = "MangaDetailsFragment";

    @BindView(R.id.cvCover)
    CardView mCoverContainer;

    @BindView(R.id.hbivChapters)
    HeadBodyItemView mChapters;

    @BindView(R.id.hbivCommunityRating)
    HeadBodyItemView mCommunityRating;

    @BindView(R.id.hbivGenres)
    HeadBodyItemView mGenres;

    @BindView(R.id.hbivMangaType)
    HeadBodyItemView mMangaType;

    @BindView(R.id.hbivVolumes)
    HeadBodyItemView mVolumes;

    @BindView(R.id.sdvCover)
    SimpleDraweeView mCover;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;


    public static MangaDetailsFragment create() {
        return new MangaDetailsFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_details, container, false);
    }

    @Override
    public void showMangaDigest(final MangaDigest mangaDigest) {
        final MangaDigest.Info info = mangaDigest.getInfo();
        final Resources resources = getResources();
        final NumberFormat numberFormat = NumberFormat.getInstance();

        if (info.hasPosterImage()) {
            mCover.setImageURI(info.getPosterImage());
            mCoverContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasType()) {
            // noinspection ConstantConditions
            mMangaType.setHead(info.getType().getTextResId());
            mMangaType.setVisibility(View.VISIBLE);
        }

        if (info.hasVolumeCount()) {
            mVolumes.setHead(numberFormat.format(info.getVolumeCount()));
            // noinspection ConstantConditions
            mVolumes.setBody(resources.getQuantityText(R.plurals.volumes,
                    info.getVolumeCount()));
            mVolumes.setVisibility(View.VISIBLE);
        }

        if (info.hasChapterCount()) {
            mChapters.setHead(numberFormat.format(info.getChapterCount()));
            // noinspection ConstantConditions
            mChapters.setBody(resources.getQuantityText(R.plurals.chapters,
                    info.getChapterCount()));
            mChapters.setVisibility(View.VISIBLE);
        }

        if (info.hasGenres()) {
            mGenres.setHead(info.getGenresString(resources));
            mGenres.setBody(resources.getQuantityText(R.plurals.genres, info.getGenresSize()));
            mGenres.setVisibility(View.VISIBLE);
        }

        if (info.hasBayesianRating()) {
            mCommunityRating.setHead(String.format(Locale.getDefault(), "%.4f",
                    info.getBayesianRating()));
            mCommunityRating.setVisibility(View.VISIBLE);
        }

        if (info.hasSynopsis()) {
            mSynopsis.setText(info.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

}
