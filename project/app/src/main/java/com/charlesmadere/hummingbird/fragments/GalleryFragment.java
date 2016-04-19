package com.charlesmadere.hummingbird.fragments;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.GalleryImage;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.Bind;

public class GalleryFragment extends BaseFragment {

    private static final String TAG = "GalleryFragment";
    private static final String KEY_GALLERY_IMAGE = "GalleryImage";

    private GalleryImage mGalleryImage;

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @Bind(R.id.sdvImage)
    SimpleDraweeView mImage;


    public static GalleryFragment create(final GalleryImage galleryImage) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_GALLERY_IMAGE, galleryImage);

        final GalleryFragment fragment = new GalleryFragment();
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
        mGalleryImage = args.getParcelable(KEY_GALLERY_IMAGE);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO
    }

    private void showError() {
        // TODO
    }

    private void showImage() {
        // TODO
    }


    private static class GetImageListener {
        private final WeakReference<GalleryFragment> mFragmentReference;

        private GetImageListener(GalleryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);

            final ControllerListener controllerListener = new BaseControllerListener() {
                @Override
                public void onFailure(final String id, final Throwable throwable) {
                    final GalleryFragment fragment = mFragmentReference.get();

                    if (fragment != null && !fragment.isDestroyed()) {
                        fragment.showError();
                    }
                }

                @Override
                public void onFinalImageSet(final String id, final Object imageInfo,
                        final Animatable animatable) {
                    final GalleryFragment fragment = mFragmentReference.get();

                    if (fragment != null && !fragment.isDestroyed()) {
                        fragment.showImage();
                    }
                }
            };
        }


    }

}
