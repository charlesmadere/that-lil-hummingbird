package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewRatingView extends LinearLayout {

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


    public ReviewRatingView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ReviewRatingView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReviewRatingView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setContent() {

    }

}
