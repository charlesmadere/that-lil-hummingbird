package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import java.util.HashMap;

public abstract class BaseMultiAdapter extends BaseAdapter<Object> {

    private final HashMap<Class, Integer> mViewKey = getItemViewKeyMap();


    public BaseMultiAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return mViewKey.get(getItem(position).getClass());
    }

    protected abstract HashMap<Class, Integer> getItemViewKeyMap();

}
