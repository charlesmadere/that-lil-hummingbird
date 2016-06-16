package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AppNews;
import com.charlesmadere.hummingbird.views.AppNewsItemView;

public class AppNewsAdapter extends BaseAdapter<AppNews> {

    private final AppNewsItemView.OnClickListener mOnClickListener;


    public AppNewsAdapter(final Context context) {
        this(context, null);
    }

    public AppNewsAdapter(final Context context,
            @Nullable final AppNewsItemView.OnClickListener onClickListener) {
        super(context);
        mOnClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_app_news;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final AppNewsItemView view = (AppNewsItemView) viewHolder.itemView;
        view.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

}
