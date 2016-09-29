package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.BaseUserActivity;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.MiscUtils;
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
        parseAttributes(null);
    }

    public AvatarView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public AvatarView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AvatarView(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
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
        if (mUser == null) {
            return;
        }

        final Context context = getContext();
        final Activity activity = MiscUtils.optActivity(context);

        if (activity instanceof BaseUserActivity && mUser.getId().equalsIgnoreCase(
                ((BaseUserActivity) activity).getUsername())) {
            return;
        }

        context.startActivity(UserActivity.getLaunchIntent(context, mUser));
    }

    private void parseAttributes(@Nullable final AttributeSet attrs) {
        final boolean enableClickListener;

        if (attrs == null) {
            enableClickListener = true;
        } else {
            final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AvatarView);
            enableClickListener = ta.getBoolean(R.styleable.AvatarView_enableClickListener, true);
            ta.recycle();
        }

        if (enableClickListener) {
            setOnClickListener(this);
        }
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
