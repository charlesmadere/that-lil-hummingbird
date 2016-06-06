package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MangaCharacterItemView extends CardView implements AdapterView<MangaDigest.Character> {

    @BindView(R.id.sdvCharacterPhoto)
    SimpleDraweeView mCharacterPhoto;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public MangaCharacterItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MangaCharacterItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final MangaDigest.Character content) {
        mCharacterPhoto.setImageURI(Uri.parse(content.getImage()));
        mTitle.setText(content.getName());
    }

}
