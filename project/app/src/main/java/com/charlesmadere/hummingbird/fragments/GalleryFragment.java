package com.charlesmadere.hummingbird.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.GalleryImage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.Bind;

public class GalleryFragment extends BaseFragment {

    private static final String TAG = "GalleryFragment";
    private static final String KEY_GALLERY_IMAGE = "GalleryImage";

    private GalleryImage mGalleryImage;

    @Bind(R.id.sdvImage)
    SimpleDraweeView mImage;

    @Bind(R.id.tvError)
    TextView mError;


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

        final ProgressBarDrawable progressBarDrawable = new ProgressBarDrawable();
        progressBarDrawable.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setProgressBarImage(progressBarDrawable)
                .build();

        mImage.setHierarchy(hierarchy);

        final ImageRequest request = ImageRequestBuilder.newBuilderWithSource(
                Uri.parse(mGalleryImage.getOriginal())).build();

        final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(final String id, final Throwable throwable) {
                        mError.setVisibility(View.VISIBLE);
                    }
                })
                .setOldController(mImage.getController())
                .setImageRequest(request)
                .build();

        mImage.setController(controller);
    }

}
