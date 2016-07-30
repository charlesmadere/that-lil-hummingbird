package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeCastingItemView extends CardView implements AdapterView<AnimeDigest.Casting> {

    @BindView(R.id.kvtvTitle)
    KeyValueTextView mTitle;

    @BindView(R.id.sdvCastPhoto)
    SimpleDraweeView mCastPhoto;

    @BindView(R.id.sdvCharacterPhoto)
    SimpleDraweeView mCharacterPhoto;


    public AnimeCastingItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeCastingItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final AnimeDigest.Casting content) {
        mCastPhoto.setImageURI(content.getPerson().getImage());

        if (content.hasCharacter()) {
            mCharacterPhoto.setImageURI(content.getCharacter().getImage());
            mCharacterPhoto.setVisibility(VISIBLE);
            mTitle.setText(content.getPerson().getName(), getResources().getString(R.string.as_x,
                    content.getCharacter().getName()));
        } else {
            mCharacterPhoto.setVisibility(GONE);
            mTitle.setText(content.getPerson().getName(), (CharSequence) null);
        }
    }

}
