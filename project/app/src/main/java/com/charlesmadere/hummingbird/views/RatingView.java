package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.LibraryUpdate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingView extends LinearLayout implements AdapterView<LibraryUpdate.Rating> {

    private static final int SIZE_SMALL = 0;
    private static final int SIZE_LARGE = 1;

    private int mSize;

    @BindView(R.id.ivStarZero)
    ImageView mStarZero;

    @BindView(R.id.ivStarOne)
    ImageView mStarOne;

    @BindView(R.id.ivStarTwo)
    ImageView mStarTwo;

    @BindView(R.id.ivStarThree)
    ImageView mStarThree;

    @BindView(R.id.ivStarFour)
    ImageView mStarFour;


    public RatingView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public RatingView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatingView(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    private void parseAttributes(final AttributeSet attrs) {

    }

    public void setContent(float rating) {
        if (rating < 0f) {
            rating = 0f;
        } else if (rating > 5f) {
            rating = 5f;
        }

        setStar(rating, mStarZero, 1f, 0f);
        setStar(rating, mStarOne, 2f, 1f);
        setStar(rating, mStarTwo, 3f, 2f);
        setStar(rating, mStarThree, 4f, 3f);
        setStar(rating, mStarFour, 5f, 4f);
    }

    @Override
    public void setContent(final LibraryUpdate.Rating content) {
        setContent(content.getValue());
    }

    private void setStar(final float rating, final ImageView view, final float full,
            final float half) {
        if (rating >= full) {
            view.setImageResource(R.drawable.ic_star_18dp);
        } else if (rating > half) {
            view.setImageResource(R.drawable.ic_star_half_18dp);
        } else {
            view.setImageResource(R.drawable.ic_star_border_18dp);
        }
    }

}
