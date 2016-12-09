package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AbsStoryV3;
import com.charlesmadere.hummingbird.models.FeedV3;

import java.util.HashMap;

public class FeedV3Adapter extends BaseMultiPaginationAdapter {

    private static final HashMap<Class, Integer> VIEW_KEY_MAP;

    private FeedV3 mFeed;


    static {
        VIEW_KEY_MAP = new HashMap<>();
        // TODO
    }

    public FeedV3Adapter(final Context context) {
        super(context, VIEW_KEY_MAP);
        setHasStableIds(true);
    }

    @Override
    public AbsStoryV3 getItem(final int position) {
        return mFeed.getStory(position);
    }

    @Override
    public int getItemCount() {
        return mFeed.getStoriesSize();
    }

    @Override
    public long getItemId(final int position) {
        return mFeed.getStory(position).hashCode();
    }

    public void set(@Nullable final FeedV3 feed) {
        if (feed != null && feed.hasStories()) {
            mFeed = feed;
        } else {
            mFeed = null;
        }

        super.set(null);
    }

}
