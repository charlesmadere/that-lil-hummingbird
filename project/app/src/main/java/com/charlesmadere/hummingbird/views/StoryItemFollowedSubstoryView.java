package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.BaseUserActivity;
import com.charlesmadere.hummingbird.models.Substory;
import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryItemFollowedSubstoryView extends FrameLayout implements View.OnClickListener {

    @Bind(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @Bind(R.id.tvUsername)
    TextView mUsername;

    private Substory mSubstory;


    public StoryItemFollowedSubstoryView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryItemFollowedSubstoryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StoryItemFollowedSubstoryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Substory getSubstory() {
        return mSubstory;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(BaseUserActivity.getLaunchIntent(context,
                mSubstory.getFollowedUser()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    public void setContent(final Substory substory) {
        mSubstory = substory;
        final User user = mSubstory.getFollowedUser();
        mAvatar.setImageURI(Uri.parse(user.getAvatarSmall()));
        mUsername.setText(user.getName());
    }

}
