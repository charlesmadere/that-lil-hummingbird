package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<AdapterView.ViewHolder> {

    private final ArrayList<T> mItems;
    private final Context mContext;


    protected BaseAdapter(final Context context) {
        mItems = new ArrayList<>();
        mContext = context;
    }

    public void add(final T item) {
        if (item != null) {
            mItems.add(item);
            notifyDataSetChanged();
        }
    }

    public void add(final List<T> items) {
        if (items != null && !items.isEmpty()) {
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (!mItems.isEmpty()) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }

    public final Context getContext() {
        return mContext;
    }

    public T getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public ArrayList<T> getItems() {
        return mItems;
    }

    @LayoutRes
    @Override
    public abstract int getItemViewType(final int position);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final AdapterView.ViewHolder holder, final int position) {
        final T item = mItems.get(position);
        holder.getAdapterView().setContent(item);
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(viewType, parent, false);
        return new AdapterView.ViewHolder(view);
    }

    public void set(final List<T> items) {
        mItems.clear();

        if (items != null && !items.isEmpty()) {
            mItems.addAll(items);
        }

        notifyDataSetChanged();
    }

}
