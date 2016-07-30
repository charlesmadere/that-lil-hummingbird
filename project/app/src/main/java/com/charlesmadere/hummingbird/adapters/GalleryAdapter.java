package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.GalleryItemView;

public class GalleryAdapter extends BaseAdapter<String> {

    private final GalleryItemView.OnClickListener mOnClickListener;


    public GalleryAdapter(final Context context) {
        this(context, null);
    }

    public GalleryAdapter(final Context context,
            @Nullable final GalleryItemView.OnClickListener onClickListener) {
        super(context);
        mOnClickListener = onClickListener;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_gallery;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final GalleryItemView view = (GalleryItemView) viewHolder.itemView;
        view.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

}
