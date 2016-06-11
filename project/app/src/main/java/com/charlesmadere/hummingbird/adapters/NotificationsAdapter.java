package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.CommentReplyNotification;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.ProfileCommentNotification;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsAdapter extends BaseMultiAdapter {

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

    public void set(final Feed feed) {
        final ArrayList<AbsNotification> notifications = feed.getNotifications();
        final ArrayList<Object> list = new ArrayList<>(notifications.size());
        list.addAll(notifications);
        set(list);
    }

}
