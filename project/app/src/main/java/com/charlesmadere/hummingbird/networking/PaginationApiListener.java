package com.charlesmadere.hummingbird.networking;

public interface PaginationApiListener<T> extends ApiListener<T> {

    void paginationComplete();
    void paginationNoMore();

}
