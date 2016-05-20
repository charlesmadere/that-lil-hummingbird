package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GalleryActivity;
import com.charlesmadere.hummingbird.adapters.GalleryAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.views.GalleryItemView;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class AnimeGalleryFragment extends BaseFragment implements GalleryItemView.OnClickListener {

    private static final String TAG = "AnimeGalleryFragment";
    private static final String KEY_ANIME_INFO = "Anime";

    private AnimeDigest.Info mAnimeInfo;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeGalleryFragment create(final AnimeDigest.Info info) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_ANIME_INFO, info);

        final AnimeGalleryFragment fragment = new AnimeGalleryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onClick(final GalleryItemView v) {
        startActivity(GalleryActivity.getLaunchIntent(getContext(), mAnimeInfo, v.getUrl()));
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mAnimeInfo = args.getParcelable(KEY_ANIME_INFO);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_gallery, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);

        if (mAnimeInfo.hasScreencaps()) {
            final GalleryAdapter adapter = new GalleryAdapter(getContext(), this);
            adapter.set(mAnimeInfo.getScreencaps());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

}
