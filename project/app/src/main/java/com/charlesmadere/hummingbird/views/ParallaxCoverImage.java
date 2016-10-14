package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
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

    @Nullable
    public Uri getUri() {
        return mUri;
    }

    public void setController(final Uri uri, final DraweeController draweeController) {
        mUri = uri;
        super.setController(draweeController);
    }

    @Override
    public void setImageURI(final Uri uri) {
        mUri = uri;
        super.setImageURI(uri);
    }

}
