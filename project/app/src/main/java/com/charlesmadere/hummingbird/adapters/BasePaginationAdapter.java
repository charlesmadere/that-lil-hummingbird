package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

public abstract class BasePaginationAdapter<T> extends BaseAdapter<T> implements
        PaginatingAdapter {

    private boolean mIsPaginating;


    public BasePaginationAdapter(final Context context) {
        super(context);
    }

    @Override
    public boolean isPaginating() {
        return mIsPaginating;
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
