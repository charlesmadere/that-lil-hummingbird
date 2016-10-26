package com.charlesmadere.hummingbird.models;

import android.support.annotation.WorkerThread;

public interface Hydratable {

    @WorkerThread
    void hydrate();

}
