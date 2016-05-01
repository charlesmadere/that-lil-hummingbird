package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Story;

public class StoriesAdapter extends BaseAdapter<Story> {

    public StoriesAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        switch (getItem(position).getType()) {
            case COMMENT:
                return R.layout.item_story_comment;

            case FOLLOWED:
                return R.layout.item_story_followed;

            case MEDIA_STORY:
                return R.layout.item_story_media_story;

            default:
                throw new IllegalArgumentException("encountered unknown " +
                        Story.Type.class.getName() + ": " + getItem(position).getType());
        }
    }

}
