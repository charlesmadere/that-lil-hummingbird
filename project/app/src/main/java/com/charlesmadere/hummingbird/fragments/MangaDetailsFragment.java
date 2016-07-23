package com.charlesmadere.hummingbird.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Manga;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;

public class MangaDetailsFragment extends BaseFragment {

    private static final String TAG = "MangaDetailsFragment";
    private static final String KEY_MANGA_DIGEST = "MangaDigest";

    private MangaDigest mMangaDigest;

    @BindView(R.id.cvCover)
    CardView mCoverContainer;

    @BindView(R.id.llChapters)
    LinearLayout mChaptersContainer;

    @BindView(R.id.llGenres)
    LinearLayout mGenresContainer;

    @BindView(R.id.llMangaType)
    LinearLayout mMangaTypeContainer;

    @BindView(R.id.llVolumes)
    LinearLayout mVolumesContainer;

    @BindView(R.id.sdvCover)
    SimpleDraweeView mCover;

    @BindView(R.id.tvChaptersBody)
    TextView mChaptersBody;

    @BindView(R.id.tvChaptersHeader)
    TextView mChaptersHeader;

    @BindView(R.id.tvGenresBody)
    TextView mGenresBody;

    @BindView(R.id.tvGenresHeader)
    TextView mGenresHeader;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvMangaType)
    TextView mMangaType;

    @BindView(R.id.tvVolumesBody)
    TextView mVolumesBody;

    @BindView(R.id.tvVolumesHeader)
    TextView mVolumesHeader;


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
            mMangaType.setText(manga.getType().getTextResId());
            mMangaTypeContainer.setVisibility(View.VISIBLE);
        }

        final NumberFormat numberFormat = NumberFormat.getInstance();
        final Resources resources = getResources();

        if (manga.hasVolumeCount()) {
            mVolumesHeader.setText(numberFormat.format(manga.getVolumeCount()));
            mVolumesBody.setText(resources.getQuantityText(R.plurals.volumes,
                    manga.getVolumeCount()));
            mVolumesContainer.setVisibility(View.VISIBLE);
        }

        if (manga.hasChapterCount()) {
            mChaptersHeader.setText(numberFormat.format(manga.getChapterCount()));
            mChaptersBody.setText(resources.getQuantityText(R.plurals.chapters,
                    manga.getChapterCount()));
            mChaptersContainer.setVisibility(View.VISIBLE);
        }

        if (manga.hasGenres()) {
            mGenresHeader.setText(manga.getGenresString(resources));
            mGenresBody.setText(resources.getQuantityText(R.plurals.genres,
                    manga.getGenres().size()));
            mGenresContainer.setVisibility(View.VISIBLE);
        }

        if (manga.hasSynopsis()) {
            mSynopsis.setText(manga.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

}
