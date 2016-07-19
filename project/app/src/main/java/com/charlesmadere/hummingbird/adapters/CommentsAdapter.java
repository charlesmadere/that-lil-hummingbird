package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.ReplySubstory;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentsAdapter extends BaseMultiPaginationAdapter {

    public CommentsAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(2);
        map.put(CommentStory.class, R.layout.item_comment_story_standalone);
        map.put(ReplySubstory.class, R.layout.item_reply_substory_standalone);
        return map;
    }

    public void set(final CommentStory commentStory, final Feed feed) {
        final ArrayList<Object> list = new ArrayList<>();
        list.add(commentStory);

        final ArrayList<AbsSubstory> substories = feed.getSubstories(AbsSubstory.Type.REPLY);
        if (substories != null && !substories.isEmpty()) {
            list.addAll(substories);
        }

        super.set(list);
    }

}
