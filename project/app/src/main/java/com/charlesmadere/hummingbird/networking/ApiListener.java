package com.charlesmadere.hummingbird.networking;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.Heartbeat;
import com.charlesmadere.hummingbird.models.ErrorInfo;

public interface ApiListener<T> extends Heartbeat {

    void failure(@Nullable final ErrorInfo error);
    void success(@Nullable final T object);

}
