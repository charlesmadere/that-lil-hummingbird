package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeReview;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeReviewVerdictView extends CardView implements AdapterView<AnimeReview> {

    @BindView(R.id.tvRatingAnimation)
    TextView mRatingAnimation;

    @BindView(R.id.tvRatingCharacters)
    TextView mRatingCharacters;

    @BindView(R.id.tvRatingEnjoyment)
    TextView mRatingEnjoyment;

    @BindView(R.id.tvRatingSound)
    TextView mRatingSound;

    @BindView(R.id.tvRatingStory)
    TextView mRatingStory;

    @BindView(R.id.tvSummary)
    TextView mSummary;

    @BindView(R.id.tvVerdictDecimal)
    TextView mVerdictDecimal;

    @BindView(R.id.tvVerdictWhole)
    TextView mVerdictWhole;


    public AnimeReviewVerdictView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeReviewVerdictView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final AnimeReview content) {
        final String rating = String.valueOf(content.getRating());
        mVerdictWhole.setText(rating.substring(0, 1));
        mVerdictDecimal.setText(rating.substring(1));

        setRating(mRatingStory, content.getRatingStory());
        setRating(mRatingAnimation, content.getRatingAnimation());
        setRating(mRatingSound, content.getRatingSound());
        setRating(mRatingCharacters, content.getRatingCharacters());
        setRating(mRatingEnjoyment, content.getRatingEnjoyment());

        mSummary.setText(content.getSummary());
    }

    private void setRating(final TextView view, final float rating) {
        view.setText(String.format(Locale.getDefault(), "%.1f", rating));
    }

}
