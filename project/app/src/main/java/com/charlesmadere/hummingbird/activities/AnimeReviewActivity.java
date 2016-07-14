package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeReview;
import com.charlesmadere.hummingbird.views.AnimeReviewVerdictView;
import com.charlesmadere.hummingbird.views.UserItemView;

import butterknife.BindView;

public class AnimeReviewActivity extends BaseDrawerActivity {

    private static final String TAG = "AnimeReviewActivity";
    private static final String CNAME = AnimeReviewActivity.class.getCanonicalName();
    private static final String EXTRA_REVIEW = CNAME + ".Review";

    @BindView(R.id.animeReviewVerdictView)
    AnimeReviewVerdictView mAnimeReviewVerdictView;

    @BindView(R.id.tvContent)
    TextView mContent;

    @BindView(R.id.userItemView)
    UserItemView mUserItemView;


    public static Intent getLaunchIntent(final Context context, final AnimeReview review) {
        return new Intent(context, AnimeReviewActivity.class)
                .putExtra(EXTRA_REVIEW, review);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_review);

        final Intent intent = getIntent();
        final AnimeReview review = intent.getParcelableExtra(EXTRA_REVIEW);
        setSubtitle(review.getAnimeTitle());

        mUserItemView.setContent(review.getUser());
        mContent.setText(review.getContent());
        mAnimeReviewVerdictView.setContent(review);
    }

}
