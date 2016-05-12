package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.models.UserDigest;

import java.util.HashMap;

public class UserDigestAdapter extends BaseMultiAdapter {

    public UserDigestAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>();
//        map.put()
        return map;
    }

    public void set(final UserDigest userDigest) {

    }

}
