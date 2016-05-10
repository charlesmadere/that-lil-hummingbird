package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.models.UserDigest;

import java.text.NumberFormat;

public class AnimeBreakdownGenreView extends KeyValueTextView {

    private NumberFormat mNumberFormat;


    public AnimeBreakdownGenreView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeBreakdownGenreView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setText(final UserDigest.Info.Genre genre) {
        setText(genre.getData().getName(), mNumberFormat.format(genre.getNum()));
    }

}
