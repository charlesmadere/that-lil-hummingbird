package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.FollowedSubstory;
import com.charlesmadere.hummingbird.views.FollowedSubstoryStandaloneItemView;

import java.util.ArrayList;
import java.util.HashMap;

public class FollowedStoryAdapter extends BaseMultiPaginationAdapter {

    private static final HashMap<Class, Integer> VIEW_KEY_MAP;


    static {
        VIEW_KEY_MAP = new HashMap<>(3);
        VIEW_KEY_MAP.put(FollowedStory.class, R.layout.item_followed_story_standalone);
        VIEW_KEY_MAP.put(FollowedSubstory.class, R.layout.item_followed_substory_standalone);
        VIEW_KEY_MAP.put(String.class, R.layout.item_charsequence_plain);
    }

    public FollowedStoryAdapter(final Context context) {
        super(context, VIEW_KEY_MAP);
    }

    @Override
    public void onBindViewHolder(final AdapterView.ViewHolder holder, final int position) {
        if (holder.getAdapterView() instanceof FollowedSubstoryStandaloneItemView) {
            final boolean showDivider = isPaginating() ? position + 2 < getItemCount()
                    : position + 1 < getItemCount();

            ((FollowedSubstoryStandaloneItemView) holder.getAdapterView()).setContent(
                    (FollowedSubstory) getItem(position), showDivider);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void set(final FollowedStory followedStory, @Nullable final Feed feed) {
        final ArrayList<Object> list = new ArrayList<>();
        list.add(followedStory);

        if (feed == null || !feed.hasSubstories()) {
            list.add(getContext().getString(R.string.nothing_more_to_show));
            super.set(list);
            return;
        }

        final ArrayList<AbsSubstory> substories = feed.getSubstories(AbsSubstory.Type.FOLLOWED);
        if (substories == null || substories.isEmpty()) {
            list.add(getContext().getString(R.string.nothing_more_to_show));
        } else {
            list.addAll(substories);
        }

        super.set(list);
    }

}
