package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.Feed;

import java.util.ArrayList;

public class FeedAdapter extends BasePaginationAdapter<AbsStory> {

    public FeedAdapter(final Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        if (isPaginating() && position == getItemCount() - 1) {
            return Long.MIN_VALUE;
        } else {
            return getItem(position).hashCode();
        }
    }

    @Override
    public int getItemViewTypeForPosition(final int position) {
        switch (getItem(position).getType()) {
            case COMMENT:
                return R.layout.item_comment_story;

            case FOLLOWED:
                return R.layout.item_followed_story;

            case MEDIA_STORY:
                return R.layout.item_media_story;

            default:
                throw new RuntimeException("encountered unknown " + AbsStory.Type.class.getName()
                        + ": \"" + getItem(position).getType() + '"');
        }
    }

    public void set(@Nullable final Feed feed) {
        if (feed != null && feed.hasStories()) {
            super.set(feed.getStories());
        } else if (feed != null && feed.hasStory()) {
            final ArrayList<AbsStory> story = new ArrayList<>(1);
            story.add(feed.getStory());
            super.set(story);
        } else {
            super.set(null);
        }
    }

}
