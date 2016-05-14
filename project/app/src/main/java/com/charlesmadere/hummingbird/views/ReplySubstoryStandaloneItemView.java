package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplySubstoryStandaloneItemView extends CardView implements AdapterView<ReplySubstory> {

    private ReplySubstory mReplySubstory;

    @BindView(R.id.kvtvReply)
    KeyValueTextView mReply;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplySubstoryStandaloneItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.sdvAvatar)
    void onAvatarClick() {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mReplySubstory.getUser()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final ReplySubstory content) {
        mReplySubstory = content;
        mAvatar.setImageURI(Uri.parse(content.getUser().getAvatarSmall()));
        mReply.setText(mReplySubstory.getUser().getName(), mReplySubstory.getReply());
        mTimeAgo.setText(mReplySubstory.getCreatedAt().getRelativeTimeText(getContext()));
    }

}
