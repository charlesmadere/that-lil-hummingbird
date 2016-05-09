package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeBreakdownView extends LinearLayout {

    @BindView(R.id.animeBreakdownPieView)
    AnimeBreakdownPieView mAnimeBreakdownPieView;

    @BindView(R.id.kvtvGenre0)
    KeyValueTextView mGenre0;

    @BindView(R.id.kvtvGenre1)
    KeyValueTextView mGenre1;

    @BindView(R.id.kvtvGenre2)
    KeyValueTextView mGenre2;

    @BindView(R.id.kvtvGenre3)
    KeyValueTextView mGenre3;

    @BindView(R.id.kvtvGenre4)
    KeyValueTextView mGenre4;

    @BindView(R.id.kvtvGenre5)
    KeyValueTextView mGenre5;

    @BindView(R.id.tvGenreCount)
    TextView mGenreCount;

    @BindView(R.id.tvPrimaryGenre)
    TextView mPrimaryGenre;


    public AnimeBreakdownView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeBreakdownView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimeBreakdownView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        if (isInEditMode()) {
            mAnimeBreakdownPieView.setValues(30, 21);
        }
    }

}
