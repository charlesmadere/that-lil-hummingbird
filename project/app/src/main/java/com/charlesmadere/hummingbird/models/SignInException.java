package com.charlesmadere.hummingbird.models;

import android.support.annotation.Nullable;

public class SignInException extends Exception {

    @Nullable
    private final ErrorInfo mErrorInfo;


    public SignInException() {
        mErrorInfo = null;
    }

    public SignInException(@Nullable final ErrorInfo errorInfo) {
        mErrorInfo = errorInfo;
    }

    @Nullable
    public ErrorInfo getErrorInfo() {
        return mErrorInfo;
    }

}
