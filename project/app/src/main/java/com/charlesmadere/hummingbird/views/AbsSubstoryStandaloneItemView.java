package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbsSubstoryStandaloneItemView extends CardView implements AdapterView<Void> {

    private int mBottomMargin;

    @BindView(R.id.absSubstoryTextView)
    AbsSubstoryTextView mAbsSubstoryTextView;

    @BindView(R.id.avatarView)
    AvatarView mAvatarView;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;

    @BindView(R.id.vDivider)
    View mDivider;


    public AbsSubstoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsSubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mBottomMargin = getResources().getDimensionPixelSize(R.dimen.root_padding);
    }

    public void setContent(final AbsSubstory content, final User user, final boolean showDivider) {
        mAvatarView.setContent(user);
        mAbsSubstoryTextView.setContent(content, user);
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));
        mDivider.setVisibility(showDivider ? VISIBLE : GONE);

        mDivider.setVisibility(showDivider ? VISIBLE : GONE);

        final MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.bottomMargin = showDivider ? mBottomMargin : 0;
        setLayoutParams(params);
    }

    @Override
    public void setContent(final Void content) {
        // intentionally empty
    }

}
