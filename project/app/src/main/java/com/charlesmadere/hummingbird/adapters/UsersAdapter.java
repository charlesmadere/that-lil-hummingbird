package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.Feed;

public class UsersAdapter extends BaseAdapter<AbsUser> {

    public UsersAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_user;
    }

    public void set(final Feed feed) {
        set(feed.getUsers());
    }

}
