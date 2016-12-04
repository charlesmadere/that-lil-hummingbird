package com.charlesmadere.hummingbird.networking;

import android.support.annotation.NonNull;

public class PaginationApiCall<T> extends ApiCall<T> implements PaginationApiListener<T> {

    public PaginationApiCall(@NonNull final PaginationApiListener<T> listener) {
        super(listener);
    }

    @Override
    public void paginationComplete() {
        final ApiListener<T> listener = getListener();

        if (listener.isAlive()) {
            ((PaginationApiListener) listener).paginationComplete();
        }
    }

    @Override
    public void paginationNoMore() {
        final ApiListener<T> listener = getListener();

        if (listener.isAlive()) {
            ((PaginationApiListener) listener).paginationNoMore();
        }
    }

}
