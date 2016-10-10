package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.CommentReplyNotification;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.ProfileCommentNotification;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsAdapter extends BaseMultiPaginationAdapter {

    private static final HashMap<Class, Integer> VIEW_KEY_MAP;


    static {
        VIEW_KEY_MAP = new HashMap<>(2);
        VIEW_KEY_MAP.put(CommentReplyNotification.class, R.layout.item_comment_reply_notification);
        VIEW_KEY_MAP.put(ProfileCommentNotification.class, R.layout.item_profile_comment_notification);
    }

    public NotificationsAdapter(final Context context) {
        super(context, VIEW_KEY_MAP);
    }

    public void set(@Nullable final Feed feed) {
        if (feed == null || !feed.hasNotifications()) {
            super.set(null);
        } else {
            super.set(new ArrayList<Object>(feed.getNotifications()));
        }
    }

}
