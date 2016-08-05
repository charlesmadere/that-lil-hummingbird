package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeCastingItemView extends CardView implements AdapterView<Void> {

    @BindView(R.id.kvtvTitle)
    KeyValueTextView mTitle;

    @BindView(R.id.sdvCastPhoto)
    SimpleDraweeView mCastPhoto;

    @BindView(R.id.sdvCharacterPhoto)
    SimpleDraweeView mCharacterPhoto;

    @BindView(R.id.divider)
    View mDivider;


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

    public void setContent(final AnimeDigest.Casting casting, final boolean showDivider) {
        mCastPhoto.setImageURI(casting.getPerson().getImage());

        if (casting.hasCharacter()) {
            mCharacterPhoto.setImageURI(casting.getCharacter().getImage());
            mCharacterPhoto.setVisibility(VISIBLE);
            mTitle.setText(casting.getPerson().getName(), getResources().getString(R.string.as_x,
                    casting.getCharacter().getName()));
        } else {
            mCharacterPhoto.setVisibility(GONE);
            mTitle.setText(casting.getPerson().getName(), (CharSequence) null);
        }

        mDivider.setVisibility(showDivider ? VISIBLE : GONE);
    }

    @Override
    public void setContent(final Void content) {
        // intentionally empty
    }

}
