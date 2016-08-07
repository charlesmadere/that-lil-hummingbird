package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class AvatarView extends SimpleDraweeView implements AdapterView<User>,
        View.OnClickListener {

    private User mUser;


    public AvatarView(final Context context, final GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public AvatarView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AvatarView(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void fetchAvatars(final User user, final String[] avatars, final int index) {
        if (mUser != user) {
            return;
        } else if (index >= avatars.length) {
            setImageURI((String) null);
            return;
        }

        final DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(final String id, final Throwable throwable) {
                        super.onFailure(id, throwable);
                        fetchAvatars(user, avatars, index + 1);
                    }
                })
                .setOldController(getController())
                .setUri(avatars[index])
                .build();

        setController(controller);
    }

    @Override
    public void onClick(final View view) {
        if (mUser != null) {
            final Context context = getContext();
            context.startActivity(UserActivity.getLaunchIntent(context, mUser));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
    }

    @Override
    public void setContent(final User content) {
        mUser = content;

        final String[] avatars = mUser.getAvatars();

        if (avatars == null || avatars.length == 0) {
            setImageURI((String) null);
            return;
        }

        boolean fetch = false;

        for (final String avatar : avatars) {
            if (!TextUtils.isEmpty(avatar)) {
                fetch = true;
                break;
            }
        }

        if (fetch) {
            fetchAvatars(mUser, avatars, 0);
        } else {
            setImageURI((String) null);
        }
    }

}
