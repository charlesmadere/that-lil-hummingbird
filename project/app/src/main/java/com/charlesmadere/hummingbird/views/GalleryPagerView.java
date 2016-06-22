package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryPagerView extends FrameLayout implements AdapterView<String> {

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.sdvImage)
    SimpleDraweeView mImage;

    @BindView(R.id.tvError)
    TextView mError;


    public static GalleryPagerView inflate(final ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return (GalleryPagerView) inflater.inflate(R.layout.item_gallery_pager, parent, false);
    }

    public GalleryPagerView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryPagerView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryPagerView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final String content) {
        mError.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);

        if (TextUtils.isEmpty(content) || TextUtils.getTrimmedLength(content) == 0) {
            mProgressBar.setVisibility(GONE);
            mError.setVisibility(VISIBLE);
            return;
        }

        final DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(final String id, final Throwable throwable) {
                        mProgressBar.setVisibility(GONE);
                        mError.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onFinalImageSet(final String id, final ImageInfo imageInfo,
                            final Animatable animatable) {
                        mError.setVisibility(GONE);
                        mProgressBar.setVisibility(GONE);
                    }
                })
                .setUri(content)
                .build();

        mImage.setController(controller);
    }

}
