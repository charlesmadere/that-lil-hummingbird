package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.GalleryImage;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;

public class GalleryItemView extends SimpleDraweeView implements AdapterView<GalleryImage>,
        View.OnClickListener {

    private GalleryImage mGalleryImage;


    public GalleryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GalleryImage getGalleryImage() {
        return mGalleryImage;
    }

    @Override
    public void onClick(final View v) {
        final Context context = v.getContext();

        if (context instanceof OnGalleryItemViewClickListener) {
            ((OnGalleryItemViewClickListener) context).onGalleryItemViewClick(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final GalleryImage content) {
        mGalleryImage = content;
        setImageURI(Uri.parse(mGalleryImage.getThumbnail()));
    }


    public interface OnGalleryItemViewClickListener {
        void onGalleryItemViewClick(final GalleryItemView v);
    }

}