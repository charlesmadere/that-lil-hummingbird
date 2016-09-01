package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class ParallaxCoverImage extends SimpleDraweeView {

    private Uri mUri;


    public ParallaxCoverImage(final Context context, final GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ParallaxCoverImage(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxCoverImage(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParallaxCoverImage(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean hasDifferentImageThan(final String uriString) {
        return !TextUtils.equals(uriString, mUri == null ? null : mUri.toString());
    }

    public void setController(final Uri uri, final DraweeController draweeController) {
        if (hasDifferentImageThan(uri == null ? null : uri.toString())) {
            mUri = uri;
            super.setController(draweeController);
        }
    }

    @Override
    public void setImageURI(final Uri uri) {
        if (hasDifferentImageThan(uri == null ? null : uri.toString())) {
            mUri = uri;
            super.setImageURI(uri);
        }
    }

}
