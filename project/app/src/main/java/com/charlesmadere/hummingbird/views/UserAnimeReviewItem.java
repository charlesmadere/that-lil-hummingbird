package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeReview;

public class UserAnimeReviewItem extends CardView implements AdapterView<AnimeReview> {

    public UserAnimeReviewItem(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public UserAnimeReviewItem(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void setContent(final AnimeReview content) {

    }

}
