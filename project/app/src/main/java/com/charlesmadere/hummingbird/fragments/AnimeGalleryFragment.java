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

public class AnimeGalleryFragment extends BaseAnimeFragment implements
        GalleryItemView.OnClickListener {

    private static final String TAG = "AnimeGalleryFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeGalleryFragment create() {
        return new AnimeGalleryFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onClick(final GalleryItemView v) {
        startActivity(GalleryActivity.getLaunchIntent(getContext(), getAnimeDigest().getInfo(),
                v.getUrl()));
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

        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);

        final AnimeDigest animeDigest = getAnimeDigest();

        if (animeDigest.getInfo().hasScreencaps()) {
            final GalleryAdapter adapter = new GalleryAdapter(getContext(), this);
            adapter.set(animeDigest.getInfo().getScreencaps());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

}
