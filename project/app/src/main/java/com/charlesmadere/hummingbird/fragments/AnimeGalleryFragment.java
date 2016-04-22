package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GalleryAdapter;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;
import com.charlesmadere.hummingbird.models.GalleryImage;

import java.util.ArrayList;

import butterknife.Bind;

public class AnimeGalleryFragment extends BaseFragment {

    private static final String TAG = "AnimeGalleryFragment";
    private static final String KEY_GALLERY_IMAGES = "GalleryImages";

    private ArrayList<GalleryImage> mGalleryImages;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tvEmpty)
    TextView mEmpty;


    public static AnimeGalleryFragment create(final ArrayList<GalleryImage> galleryImages) {
        final Bundle args = new Bundle(1);
        args.putParcelableArrayList(KEY_GALLERY_IMAGES, galleryImages);

        final AnimeGalleryFragment fragment = new AnimeGalleryFragment();
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
        mGalleryImages = args.getParcelableArrayList(KEY_GALLERY_IMAGES);
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

        if (mGalleryImages == null || mGalleryImages.isEmpty()) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            final GalleryAdapter adapter = new GalleryAdapter(getContext());
            adapter.set(mGalleryImages);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
