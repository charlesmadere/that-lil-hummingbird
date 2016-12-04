package com.charlesmadere.hummingbird.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.ErrorInfo;

import java.lang.ref.WeakReference;

public class ApiCall<T> implements ApiListener<T> {

    private final WeakReference<ApiListener<T>> mReference;


    public ApiCall(@NonNull final ApiListener<T> listener) {
        mReference = new WeakReference<>(listener);
    }

    @Override
    public void failure(@Nullable final ErrorInfo error) {
        final ApiListener<T> listener = getListener();

        if (listener.isAlive()) {
            listener.failure(error);
        }
    }

    public ApiListener<T> getListener() {
        return mReference.get();
    }

    @Override
    public boolean isAlive() {
        final ApiListener<T> listener = getListener();
        return listener != null && listener.isAlive();
    }

    @Override
    public void success(@Nullable final T object) {
        final ApiListener<T> listener = getListener();

        if (listener.isAlive()) {
            listener.success(object);
        }
    }

}
