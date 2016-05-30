package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeReviewActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimeReviewItemView extends CardView implements AdapterView<AnimeDigest.Review>,
        View.OnClickListener {

    private AnimeDigest.Review mReview;

    @BindView(R.id.avatarView)
    AvatarView mAvatarView;


    public AnimeReviewItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeReviewItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.avatarView)
    void onAvatarClick() {

    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(AnimeReviewActivity.getLaunchIntent(context, mReview));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final AnimeDigest.Review content) {
        mReview = content;
    }

}
