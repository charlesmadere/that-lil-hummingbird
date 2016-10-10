package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import java.util.HashMap;

public abstract class BaseMultiAdapter extends BaseAdapter<Object> {

    private final HashMap<Class, Integer> mViewKeyMap;


    public BaseMultiAdapter(final Context context, final HashMap<Class, Integer> viewKeyMap) {
        super(context);
        mViewKeyMap = viewKeyMap;
    }

    @Override
    public int getItemViewType(final int position) {
        return mViewKeyMap.get(getItem(position).getClass());
    }

}
