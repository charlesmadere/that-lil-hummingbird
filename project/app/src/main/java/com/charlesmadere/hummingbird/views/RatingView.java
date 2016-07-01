package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Rating;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingView extends LinearLayout implements AdapterView<Rating> {

    private static final int STAR_SIZE_SMALL = 0;
    private static final int STAR_SIZE_LARGE = 1;

    private int mStarSize;
    private ViewHolder mViewHolder;

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

    @Nullable
    @BindView(R.id.tvNoRating)
    TextView mNoRating;


    public static RatingView inflate(final ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return (RatingView) inflater.inflate(R.layout.item_rating_view, parent, false);
    }

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

    public ViewHolder getViewHolder() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
        }

        return mViewHolder;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RatingView);
        mStarSize = ta.getInt(R.styleable.RatingView_star_size, STAR_SIZE_SMALL);
        ta.recycle();

        if (mStarSize != STAR_SIZE_SMALL && mStarSize != STAR_SIZE_LARGE) {
            throw new RuntimeException("star size is invalid: " + mStarSize);
        }
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
    public void setContent(@Nullable final Rating content) {
        if (content == null || Rating.UNRATED == content) {
            if (mNoRating == null) {
                setContent(Rating.ZERO);
            } else {
                mStarZero.setVisibility(GONE);
                mStarOne.setVisibility(GONE);
                mStarTwo.setVisibility(GONE);
                mStarThree.setVisibility(GONE);
                mStarFour.setVisibility(GONE);
                mNoRating.setVisibility(VISIBLE);
            }
        } else {
            if (mNoRating != null) {
                mNoRating.setVisibility(GONE);
            }

            setContent(content.mValue);
        }
    }

    private void setStar(final float rating, final ImageView view, final float full,
            final float half) {
        if (rating >= full) {
            if (mStarSize == STAR_SIZE_SMALL) {
                view.setImageResource(R.drawable.ic_star_18dp);
            } else {
                view.setImageResource(R.drawable.ic_star_24dp);
            }
        } else if (rating > half) {
            if (mStarSize == STAR_SIZE_SMALL) {
                view.setImageResource(R.drawable.ic_star_half_18dp);
            } else {
                view.setImageResource(R.drawable.ic_star_half_24dp);
            }
        } else {
            if (mStarSize == STAR_SIZE_SMALL) {
                view.setImageResource(R.drawable.ic_star_border_18dp);
            } else {
                view.setImageResource(R.drawable.ic_star_border_24dp);
            }
        }

        view.setVisibility(VISIBLE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder() {
            super(RatingView.this);
        }

        public RatingView getView() {
            return RatingView.this;
        }
    }

}
