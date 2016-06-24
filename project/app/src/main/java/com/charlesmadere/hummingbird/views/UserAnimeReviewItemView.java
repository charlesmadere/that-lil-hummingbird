package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeReviewActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeReview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAnimeReviewItemView extends CardView implements AdapterView<AnimeReview>,
        View.OnClickListener {

    private AnimeReview mAnimeReview;

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

    @Override
    public void onClick(final View view) {
        final Context context = getContext();
        context.startActivity(AnimeReviewActivity.getLaunchIntent(context, mAnimeReview));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final AnimeReview content) {
        mAnimeReview = content;

    }

}
