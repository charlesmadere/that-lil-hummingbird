package com.charlesmadere.hummingbird.networking;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.ErrorInfo;

public class ServerException extends Exception {

    @Nullable
    private final ErrorInfo mErrorInfo;


    public ServerException() {
        mErrorInfo = null;
    }

    public ServerException(@Nullable final ErrorInfo errorInfo) {
        mErrorInfo = errorInfo;
    }

    @Nullable
    public ErrorInfo getErrorInfo() {
        return mErrorInfo;
    }

}
