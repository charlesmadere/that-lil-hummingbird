package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeReviewActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeReview;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAnimeReviewItemView extends FrameLayout implements AdapterView<AnimeReview>,
        View.OnClickListener {

    private AnimeReview mReview;
    private NumberFormat mNumberFormat;

    @BindView(R.id.reviewRatingView)
    RatingView mRatingView;

    @BindView(R.id.tvReviewHelpfulness)
    TextView mReviewHelpfulness;

    @BindView(R.id.tvSummary)
    TextView mSummary;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public UserAnimeReviewItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public UserAnimeReviewItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UserAnimeReviewItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View view) {
        final Context context = getContext();
        context.startActivity(AnimeReviewActivity.getLaunchIntent(context, mReview));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AnimeReview content) {
        mReview = content;
        mTitle.setText(content.getAnimeTitle());
        mRatingView.setContent(content.getRating());
        mSummary.setText(mReview.getSummary());
        mReviewHelpfulness.setText(getResources().getString(
                R.string.x_out_of_y_users_found_this_review_helpful,
                mNumberFormat.format(mReview.getPositiveVotes()),
                mNumberFormat.format(mReview.getTotalVotes())));
    }

}
