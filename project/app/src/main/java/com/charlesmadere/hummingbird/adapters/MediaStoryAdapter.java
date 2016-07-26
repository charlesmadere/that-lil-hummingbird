package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;
import com.charlesmadere.hummingbird.views.AbsSubstoryItemView;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaStoryAdapter extends BaseMultiPaginationAdapter {

    private User mUser;


    public MediaStoryAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(4);
        map.put(MediaStory.class, R.layout.item_media_story_header);
        map.put(String.class, R.layout.item_charsequence_plain);
        map.put(WatchedEpisodeSubstory.class, R.layout.item_abs_substory_standalone);
        map.put(WatchlistStatusUpdateSubstory.class, R.layout.item_abs_substory_standalone);
        return map;
    }

    @Override
    public void onBindViewHolder(final AdapterView.ViewHolder holder, final int position) {
        if (holder.getAdapterView() instanceof AbsSubstoryItemView) {
            ((AbsSubstoryItemView) holder.getAdapterView()).setContent(
                    (AbsSubstory) getItem(position), mUser);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void set(final MediaStory mediaStory, final Feed feed) {
        mUser = mediaStory.getUser();
        final ArrayList<Object> list = new ArrayList<>();
        list.add(mediaStory);

        final ArrayList<AbsSubstory> substories = feed.getSubstories();
        if (substories == null || substories.isEmpty()) {
            list.add(getContext().getString(R.string.nothing_more_to_show));
        } else {
            list.addAll(substories);
        }

        super.set(list);
    }

}
