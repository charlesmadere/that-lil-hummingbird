package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.User;

public class UsersAdapter extends BasePaginationAdapter<User> {

    public UsersAdapter(final Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_user;
    }

    public void set(final Feed feed) {
        super.set(feed.getUsers());
    }

}
