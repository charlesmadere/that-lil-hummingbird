package com.charlesmadere.hummingbird.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Manga;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;

public class MangaDetailsFragment extends BaseFragment {

    private static final String TAG = "MangaDetailsFragment";
    private static final String KEY_MANGA_DIGEST = "MangaDigest";

    private MangaDigest mMangaDigest;

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


    public static MangaDetailsFragment create(final MangaDigest digest) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_MANGA_DIGEST, digest);

        final MangaDetailsFragment fragment = new MangaDetailsFragment();
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
        mMangaDigest = args.getParcelable(KEY_MANGA_DIGEST);
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

        final Manga manga = mMangaDigest.getManga();

        if (manga.hasPosterImage()) {
            mCover.setImageURI(manga.getPosterImage());
            mCoverContainer.setVisibility(View.VISIBLE);
        }

        if (manga.hasType()) {
            mMangaType.setHead(manga.getType().getTextResId());
            mMangaType.setVisibility(View.VISIBLE);
        }

        final NumberFormat numberFormat = NumberFormat.getInstance();
        final Resources resources = getResources();

        if (manga.hasVolumeCount()) {
            mVolumes.setHead(numberFormat.format(manga.getVolumeCount()));
            mVolumes.setBody(resources.getQuantityText(R.plurals.volumes,
                    manga.getVolumeCount()));
            mVolumes.setVisibility(View.VISIBLE);
        }

        if (manga.hasChapterCount()) {
            mChapters.setHead(numberFormat.format(manga.getChapterCount()));
            mChapters.setBody(resources.getQuantityText(R.plurals.chapters,
                    manga.getChapterCount()));
            mChapters.setVisibility(View.VISIBLE);
        }

        if (manga.hasGenres()) {
            mGenres.setHead(manga.getGenresString(resources));
            mGenres.setBody(resources.getQuantityText(R.plurals.genres,
                    manga.getGenres().size()));
            mGenres.setVisibility(View.VISIBLE);
        }

        if (manga.hasSynopsis()) {
            mSynopsis.setText(manga.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

}
