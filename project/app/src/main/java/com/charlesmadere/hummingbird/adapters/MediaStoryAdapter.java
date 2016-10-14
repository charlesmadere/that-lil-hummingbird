package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;
import com.charlesmadere.hummingbird.views.AbsSubstoryStandaloneItemView;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaStoryAdapter extends BaseMultiPaginationAdapter {

    private static final HashMap<Class, Integer> VIEW_KEY_MAP;

    private User mUser;


    static {
        VIEW_KEY_MAP = new HashMap<>(4);
        VIEW_KEY_MAP.put(MediaStory.class, R.layout.item_media_story_standalone);
        VIEW_KEY_MAP.put(String.class, R.layout.item_charsequence_plain);
        VIEW_KEY_MAP.put(WatchedEpisodeSubstory.class, R.layout.item_abs_substory_standalone);
        VIEW_KEY_MAP.put(WatchlistStatusUpdateSubstory.class, R.layout.item_abs_substory_standalone);
    }

    public MediaStoryAdapter(final Context context) {
        super(context, VIEW_KEY_MAP);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        if (isPaginating() && position == getItemCount() - 1) {
            return Long.MIN_VALUE;
        }

        final Object item = getItem(position);

        if (item instanceof MediaStory) {
            return Long.MIN_VALUE + 1L;
        } else if (item instanceof String) {
            return Long.MIN_VALUE + 2L;
        } else {
            return item.hashCode();
        }
    }

    @Override
    public void onBindViewHolder(final AdapterView.ViewHolder holder, final int position) {
        if (holder.getAdapterView() instanceof AbsSubstoryStandaloneItemView) {
            final boolean showDivider = isPaginating() ? position + 2 < getItemCount()
                    : position + 1 < getItemCount();

            ((AbsSubstoryStandaloneItemView) holder.getAdapterView()).setContent(
                    (AbsSubstory) getItem(position), mUser, showDivider);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void set(final MediaStory mediaStory, @Nullable final Feed feed) {
        mUser = mediaStory.getUser();
        final ArrayList<Object> list = new ArrayList<>();
        list.add(mediaStory);

        if (feed == null || !feed.hasSubstories()) {
            list.add(getContext().getString(R.string.nothing_more_to_show));
        } else {
            list.addAll(feed.getSubstories());
        }

        super.set(list);
    }

}
