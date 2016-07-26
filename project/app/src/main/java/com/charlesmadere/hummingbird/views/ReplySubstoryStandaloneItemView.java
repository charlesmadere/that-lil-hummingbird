package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.ReplySubstory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplySubstoryStandaloneItemView extends CardView implements AdapterView<ReplySubstory> {

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.replyTextView)
    ReplyTextView mReply;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final ReplySubstory content) {
        mAvatar.setContent(content.getUser());
        mReply.setContent(content);
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));
    }

}
