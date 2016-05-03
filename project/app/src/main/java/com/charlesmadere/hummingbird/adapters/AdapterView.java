package com.charlesmadere.hummingbird.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface AdapterView<T> {

    void setContent(final T content);


    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(final View itemView) {
            super(itemView);

            if (!(itemView instanceof AdapterView)) {
                throw new IllegalArgumentException("itemView (" +
                        itemView.getClass().getSimpleName() + ") must implement " +
                        AdapterView.class.getSimpleName());
            }
        }

        public AdapterView getAdapterView() {
            return (AdapterView) itemView;
        }
    }

}
