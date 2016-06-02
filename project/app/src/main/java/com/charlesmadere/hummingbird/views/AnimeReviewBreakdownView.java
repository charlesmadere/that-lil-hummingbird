package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeReviewBreakdownView extends CardView implements AdapterView<AnimeDigest.Review> {

    @BindView(R.id.tvAnimationRating)
    TextView mAnimationRating;

    @BindView(R.id.tvCharactersRating)
    TextView mCharactersRating;

    @BindView(R.id.tvVerdictDecimal)
    TextView mDecimalVerdict;

    @BindView(R.id.tvEnjoymentRating)
    TextView mEjoymentRating;

    @BindView(R.id.tvSoundRating)
    TextView mSoundRating;

    @BindView(R.id.tvStoryRating)
    TextView mStoryRating;

    @BindView(R.id.tvVerdictWhole)
    TextView mWholeVerdict;


    public AnimeReviewBreakdownView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeReviewBreakdownView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final AnimeDigest.Review content) {
        final String rating = String.valueOf(content.getRating());
        mWholeVerdict.setText(rating.substring(0, 1));
        mDecimalVerdict.setText(rating.substring(1));

        setRating(mStoryRating, content.getRatingStory());
        setRating(mAnimationRating, content.getRatingAnimation());
        setRating(mSoundRating, content.getRatingSound());
        setRating(mCharactersRating, content.getRatingCharacters());
        setRating(mEjoymentRating, content.getRatingEnjoyment());
    }

    private void setRating(final TextView view, final float rating) {
        view.setText(String.format(Locale.getDefault(), "%.1f", rating));
    }

}
