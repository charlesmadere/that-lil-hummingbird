package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MediaStory;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaStoryAdapter extends BaseMultiPaginationAdapter {

    public MediaStoryAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(3);
        map.put(AbsSubstory.class, R.layout.item_media_substory_standalone);
        map.put(CharSequence.class, R.layout.item_charsequence_plain);
        map.put(MediaStory.class, R.layout.item_media_story_header);
        return map;
    }

    public void set(final MediaStory mediaStory, final Feed feed) {
        final ArrayList<Object> list = new ArrayList<>();
        list.add(mediaStory);

        final ArrayList<AbsSubstory> substories = feed.getSubstories();
        if (substories == null || substories.isEmpty()) {
            list.add(getContext().getText(R.string.nothing_more_to_show));
        } else {
            list.addAll(substories);
        }

        super.set(list);
    }

}
