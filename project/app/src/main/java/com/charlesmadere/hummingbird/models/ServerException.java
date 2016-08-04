package com.charlesmadere.hummingbird.models;

import android.support.annotation.Nullable;

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
