package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class AvatarView extends SimpleDraweeView {

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
        if (mUser != user || index >= avatars.length) {
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
                .setUri(avatars[index])
                .build();

        setController(controller);
    }

    public void setContent(final User content) {
        mUser = content;
        fetchAvatars(mUser, mUser.getAvatars(), 0);
    }

}
