package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.ReplySubstory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplySubstoryStandaloneItemView extends FrameLayout implements AdapterView<Void> {

    private int mBottomMargin;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.replyTextView)
    ReplyTextView mReply;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;

    @BindView(R.id.divider)
    View mDivider;


    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mBottomMargin = getResources().getDimensionPixelSize(R.dimen.root_padding_half);
    }

    public void setContent(final ReplySubstory content, final boolean showDivider) {
        mAvatar.setContent(content.getUser());
        mReply.setContent(content);
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));

        final MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();

        if (showDivider) {
            mDivider.setVisibility(VISIBLE);
            params.bottomMargin = 0;
        } else {
            mDivider.setVisibility(GONE);
            params.bottomMargin = mBottomMargin;
        }

        setLayoutParams(params);
    }

    @Override
    public void setContent(final Void content) {
        // intentionally empty
    }

}
