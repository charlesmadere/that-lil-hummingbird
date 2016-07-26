package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.ReplySubstory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplySubstoryItemView extends RelativeLayout {

    private ReplySubstory mReplySubstory;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.replyTextView)
    ReplyTextView mReply;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public ReplySubstoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplySubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReplySubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }

    public void setContent(final ReplySubstory content) {
        mReplySubstory = content;
        mAvatar.setContent(mReplySubstory.getUser());
        mReply.setContent(mReplySubstory);
        mTimeAgo.setText(mReplySubstory.getCreatedAt().getRelativeTimeText(getContext()));
    }

}
