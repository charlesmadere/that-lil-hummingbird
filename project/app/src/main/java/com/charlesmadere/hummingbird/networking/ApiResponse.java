package com.charlesmadere.hummingbird.networking;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.ErrorInfo;

public interface ApiResponse<T> {

    void failure(@Nullable final ErrorInfo error);
    void success(@Nullable final T object);

}
