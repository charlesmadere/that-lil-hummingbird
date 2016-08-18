package com.charlesmadere.hummingbird.networking;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.ErrorInfo;

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
