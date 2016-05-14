package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.ProfileCommentNotification;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileCommentNotificationItemView extends CardView implements
        AdapterView<ProfileCommentNotification> {

    private ProfileCommentNotification mProfileCommentNotification;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;


    public ProfileCommentNotificationItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileCommentNotificationItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.sdvAvatar)
    void onAvatarClick() {

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
    public void setContent(final ProfileCommentNotification content) {
        mProfileCommentNotification = content;
    }

}
