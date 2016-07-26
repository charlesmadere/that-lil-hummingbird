package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbsSubstoryItemView extends RelativeLayout {

    @BindView(R.id.absSubstoryTextView)
    AbsSubstoryTextView mAbsSubstoryTextView;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public AbsSubstoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setContent(final AbsSubstory content, final User user) {
        mAvatar.setContent(user);
        mAbsSubstoryTextView.setContent(content, user);
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));
    }

}
