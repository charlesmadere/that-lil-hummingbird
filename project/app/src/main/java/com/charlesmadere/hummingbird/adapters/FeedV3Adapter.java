package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import java.util.HashMap;

public class FeedV3Adapter extends BaseMultiPaginationAdapter {

    private static final HashMap<Class, Integer> VIEW_KEY_MAP;


    static {
        VIEW_KEY_MAP = new HashMap<>();
        // TODO
    }

    public FeedV3Adapter(final Context context, final HashMap<Class, Integer> viewKeyMap) {
        super(context, viewKeyMap);
        setHasStableIds(true);
    }



}