package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.views.AnimeReviewVerdictView;

import butterknife.BindView;

public class AnimeReviewActivity extends BaseDrawerActivity {

    private static final String TAG = "AnimeReviewActivity";
    private static final String CNAME = AnimeReviewActivity.class.getCanonicalName();
    private static final String EXTRA_REVIEW = CNAME + ".Review";

    private AnimeDigest.Review mReview;

    @BindView(R.id.animeReviewVerdictView)
    AnimeReviewVerdictView mAnimeReviewVerdictView;

    @BindView(R.id.tvContent)
    TextView mContent;


    public static Intent getLaunchIntent(final Context context, final AnimeDigest.Review review) {
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
        mReview = intent.getParcelableExtra(EXTRA_REVIEW);
        setTitle(getString(R.string.review_by_x, mReview.getUserId()));

        mContent.setText(mReview.getContent());
        mAnimeReviewVerdictView.setContent(mReview);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_anime_review, menu);

        final MenuItem user = menu.findItem(R.id.miViewUser);
        user.setTitle(getString(R.string.view_x, mReview.getUserId()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miViewUser:
                final User user = mReview.getUser();
                startActivity(UserActivity.getLaunchIntent(this, user));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
