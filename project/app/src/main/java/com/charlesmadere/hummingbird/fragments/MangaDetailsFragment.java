package com.charlesmadere.hummingbird.fragments;

import android.content.Context;
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

import butterknife.BindView;

public class MangaDetailsFragment extends BaseMangaFragment {

    private static final String TAG = "MangaDetailsFragment";

    @BindView(R.id.cvCover)
    CardView mCoverContainer;

    @BindView(R.id.hbivChapters)
    HeadBodyItemView mChapters;

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
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MangaDigest mangaDigest = getMangaDigest();
        final MangaDigest.Info info = mangaDigest.getInfo();
        final Context context = getContext();
        final Resources resources = getResources();
        final NumberFormat numberFormat = NumberFormat.getInstance();

        if (info.hasPosterImage()) {
            mCover.setImageURI(info.getPosterImage());
            mCoverContainer.setVisibility(View.VISIBLE);
        }

        if (info.hasType()) {
            mMangaType.setHead(info.getType().getTextResId());
            mMangaType.setVisibility(View.VISIBLE);
        }

        if (info.hasVolumeCount()) {
            mVolumes.setHead(numberFormat.format(info.getVolumeCount()));
            mVolumes.setBody(resources.getQuantityText(R.plurals.volumes,
                    info.getVolumeCount()));
            mVolumes.setVisibility(View.VISIBLE);
        }

        if (info.hasChapterCount()) {
            mChapters.setHead(numberFormat.format(info.getChapterCount()));
            mChapters.setBody(resources.getQuantityText(R.plurals.chapters,
                    info.getChapterCount()));
            mChapters.setVisibility(View.VISIBLE);
        }

        if (info.hasGenres()) {
            mGenres.setHead(info.getGenresString(resources));
            mGenres.setBody(resources.getQuantityText(R.plurals.genres, info.getGenresSize()));
            mGenres.setVisibility(View.VISIBLE);
        }

        if (info.hasSynopsis()) {
            mSynopsis.setText(info.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

}
