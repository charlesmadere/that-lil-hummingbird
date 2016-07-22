package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.Group;

public class GroupsAdapter extends BasePaginationAdapter<Group> {

    public GroupsAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_group;
    }

    public void set(final Feed feed) {
        super.set(feed.getGroups());
    }

}
