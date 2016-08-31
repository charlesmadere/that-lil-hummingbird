package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeReviewActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.AnimeReview;
import com.charlesmadere.hummingbird.models.UiColorSet;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeReviewItemView extends FrameLayout implements AdapterView<AnimeReview>,
        View.OnClickListener {

    private AnimeReview mReview;
    private NumberFormat mNumberFormat;

    @BindView(R.id.avatarView)
    AvatarView mAvatarView;

    @BindView(R.id.tvRating)
    TextView mRating;

    @BindView(R.id.tvReviewHelpfulness)
    TextView mReviewHelpfulness;

    @BindView(R.id.tvSummary)
    TextView mSummary;


    public AnimeReviewItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeReviewItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimeReviewItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View v) {
        final Activity activity = MiscUtils.getActivity(getContext());
        final UiColorSet uiColorSet = activity instanceof PaletteUtils.Listener ?
                ((PaletteUtils.Listener) activity).getUiColorSet() : null;

        activity.startActivity(AnimeReviewActivity.getLaunchIntent(activity, mReview, uiColorSet));
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
        mAvatarView.setContent(mReview.getUser());
        mRating.setText(String.format(Locale.getDefault(), "%.1f", mReview.getRating()));
        mSummary.setText(getResources().getString(R.string.review_format, mReview.getSummary()));
        mReviewHelpfulness.setText(getResources().getString(
                R.string.x_out_of_y_users_found_this_review_helpful,
                mNumberFormat.format(mReview.getPositiveVotes()),
                mNumberFormat.format(mReview.getTotalVotes())));
    }

}
