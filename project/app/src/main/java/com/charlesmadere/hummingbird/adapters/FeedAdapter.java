package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.Feed;

public class FeedAdapter extends BaseAdapter<AbsStory> {

    public FeedAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        switch (getItem(position).getType()) {
            case COMMENT:
                return R.layout.item_comment_story;

            case MEDIA_STORY:
                return R.layout.item_media_story;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsStory.Type.class.getName() + ": " + getItem(position).getType());
        }
    }

    public void set(final Feed feed) {
        super.set(feed.getStories());
    }

}
