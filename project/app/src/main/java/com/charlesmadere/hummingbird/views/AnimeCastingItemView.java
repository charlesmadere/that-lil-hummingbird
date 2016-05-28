package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;

public class AnimeCastingItemView extends CardView implements AdapterView<AnimeDigest.Casting> {

    public AnimeCastingItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeCastingItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setContent(final AnimeDigest.Casting content) {

    }

}
