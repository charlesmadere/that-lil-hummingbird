package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;

import java.util.HashMap;

public abstract class BaseMultiPaginationAdapter extends BaseMultiAdapter implements
        PaginatingAdapter {

    private boolean mIsPaginating;


    public BaseMultiPaginationAdapter(final Context context,
            final HashMap<Class, Integer> viewKeyMap) {
        super(context, viewKeyMap);
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();

        if (mIsPaginating) {
            ++itemCount;
        }

        return itemCount;
    }

    @Override
    public final int getItemViewType(final int position) {
        if (mIsPaginating && position == getItems().size()) {
            return R.layout.item_progress;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public boolean isPaginating() {
        return mIsPaginating;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final AdapterView.ViewHolder holder, final int position) {
        if (mIsPaginating && position == getItems().size()) {
            holder.getAdapterView().setContent(null);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public void setPaginating(final boolean paginating) {
        if (mIsPaginating == paginating) {
            return;
        }

        mIsPaginating = paginating;

        if (mIsPaginating) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount() + 1);
        }
    }

}
