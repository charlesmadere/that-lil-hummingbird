package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.GroupMember;

public class GroupMembersAdapter extends BaseAdapter<GroupMember> {

    public GroupMembersAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_group_member;
    }

    public void set(final Feed feed) {
        set(feed.getGroupMembers());
    }

}
