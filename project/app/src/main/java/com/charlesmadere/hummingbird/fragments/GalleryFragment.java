package com.charlesmadere.hummingbird.fragments;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.GalleryImage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;

public class GalleryFragment extends BaseFragment {

    private static final String TAG = "GalleryFragment";
    private static final String KEY_URL = "Url";

    private String mUrl;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.sdvImage)
    SimpleDraweeView mImage;

    @BindView(R.id.tvError)
    TextView mError;


    public static GalleryFragment create(final GalleryImage galleryImage) {
        return create(galleryImage.getOriginal());
    }

    public static GalleryFragment create(final String url) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_URL, url);

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
        mUrl = args.getString(KEY_URL);
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

        final ImageRequest request = ImageRequestBuilder.newBuilderWithSource(
                Uri.parse(mUrl)).build();

        final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(final String id, final Throwable throwable) {
                        mProgressBar.setVisibility(View.GONE);
                        mError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinalImageSet(final String id, final ImageInfo imageInfo,
                            final Animatable animatable) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                })
                .setOldController(mImage.getController())
                .setImageRequest(request)
                .build();

        mImage.setController(controller);
    }

}
