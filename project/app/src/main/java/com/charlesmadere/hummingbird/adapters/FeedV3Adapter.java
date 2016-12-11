package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsStoryV3;
import com.charlesmadere.hummingbird.models.CommentStoryV3;
import com.charlesmadere.hummingbird.models.FeedV3;
import com.charlesmadere.hummingbird.models.FollowStory;
import com.charlesmadere.hummingbird.models.PostStory;
import com.charlesmadere.hummingbird.models.ProgressedStory;
import com.charlesmadere.hummingbird.models.RatedStory;
import com.charlesmadere.hummingbird.models.ReviewedStory;
import com.charlesmadere.hummingbird.models.UpdatedStory;

import java.util.HashMap;
import java.util.List;

public class FeedV3Adapter extends BaseMultiPaginationAdapter {

    private static final HashMap<Class, Integer> VIEW_KEY_MAP;

    private FeedV3 mFeed;


    static {
        VIEW_KEY_MAP = new HashMap<>(7);
        VIEW_KEY_MAP.put(CommentStoryV3.class, R.layout.item_comment_story_v3);
        VIEW_KEY_MAP.put(FollowStory.class, R.layout.item_follow_story);
        VIEW_KEY_MAP.put(PostStory.class, R.layout.item_post_story);
        VIEW_KEY_MAP.put(ProgressedStory.class, R.layout.item_progressed_story);
        VIEW_KEY_MAP.put(RatedStory.class, R.layout.item_rated_story);
        VIEW_KEY_MAP.put(ReviewedStory.class, R.layout.item_reviewed_story);
        VIEW_KEY_MAP.put(UpdatedStory.class, R.layout.item_updated_story);
    }

    public FeedV3Adapter(final Context context) {
        super(context, VIEW_KEY_MAP);
        setHasStableIds(true);
    }

    @Override
    public void add(@Nullable final Object item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(@Nullable final List<Object> items) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        if (mFeed != null) {
            mFeed = null;
            notifyDataSetChanged();
        }
    }

    @Override
    public AbsStoryV3 getItem(final int position) {
        return mFeed.getStory(position);
    }

    @Override
    public int getItemCount() {
        int itemCount = mFeed.getStoriesSize();

        if (isPaginating()) {
            ++itemCount;
        }

        return itemCount;
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

        notifyDataSetChanged();
    }

    @Override
    public void set(@Nullable final List<Object> items) {
        throw new UnsupportedOperationException();
    }

}
