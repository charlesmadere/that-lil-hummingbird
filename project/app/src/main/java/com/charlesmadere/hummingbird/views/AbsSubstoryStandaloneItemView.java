package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbsSubstoryStandaloneItemView extends CardView {

    @BindView(R.id.absSubstoryTextView)
    AbsSubstoryTextView mAbsSubstoryTextView;

    @BindView(R.id.avatarView)
    AvatarView mAvatarView;


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
    }

    public void setContent(final AbsSubstory content, final User user) {
        mAvatarView.setContent(user);
        mAbsSubstoryTextView.setContent(content, user);
    }

}
