package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;

public class GalleryItemView extends SimpleDraweeView implements AdapterView<String> {

    private String mUrl;


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

    public String getUrl() {
        return mUrl;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final String content) {
        mUrl = content;
        setImageURI(Uri.parse(mUrl));
    }

    public void setOnClickListener(final OnClickListener l) {
        if (l == null) {
            setClickable(false);
        } else {
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    l.onClick(GalleryItemView.this);
                }
            });
        }
    }


    public interface OnClickListener {
        void onClick(final GalleryItemView v);
    }

}
