package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAnimeView extends CardView {

    @BindView(R.id.tvNoFavorites)
    TextView mNoFavorites;


    public FavoriteAnimeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteAnimeView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }



}
