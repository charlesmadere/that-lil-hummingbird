package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.GroupMember;

public class GroupMembersAdapter extends BasePaginationAdapter<GroupMember> {

    public GroupMembersAdapter(final Context context) {
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

    @LayoutRes
    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_group_member;
    }

    public void set(@Nullable final Feed feed) {
        if (feed == null) {
            super.set(null);
        } else {
            super.set(feed.getGroupMembers());
        }
    }

}
