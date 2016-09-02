package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.User;

public class UsersAdapter extends BasePaginationAdapter<User> {

    public UsersAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_user;
    }

    public void set(@Nullable final Feed feed) {
        if (feed == null) {
            super.set(null);
        } else {
            super.set(feed.getUsers());
        }
    }

}
