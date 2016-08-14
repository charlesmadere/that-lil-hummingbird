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

    public NotificationsAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(2);
        map.put(CommentReplyNotification.class, R.layout.item_comment_reply_notification);
        map.put(ProfileCommentNotification.class, R.layout.item_profile_comment_notification);
        return map;
    }

    public void set(@Nullable final Feed feed) {
        if (feed == null) {
            super.set(null);
        } else {
            super.set(new ArrayList<Object>(feed.getNotifications()));
        }
    }

}
