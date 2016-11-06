package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.charlesmadere.hummingbird.R;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Collections;

public final class Timber {

    private static final ArrayList<BaseEntry> ENTRIES;
    private static final int ENTRIES_MAX_SIZE;


    static {
        if (MiscUtils.isLowRamDevice()) {
            ENTRIES_MAX_SIZE = 48;
        } else {
            ENTRIES_MAX_SIZE = 96;
        }

        ENTRIES = new ArrayList<>(ENTRIES_MAX_SIZE);
    }

    private static synchronized void addEntry(final BaseEntry entry) {
        while (ENTRIES.size() >= ENTRIES_MAX_SIZE) {
            ENTRIES.remove(ENTRIES.size() - 1);
        }

        ENTRIES.add(entry);
    }

    public static synchronized void clearEntries() {
        ENTRIES.clear();
    }

    public static void d(final String tag, final String msg) {
        d(tag, msg, null);
    }

    public static void d(final String tag, final String msg, @Nullable final Throwable tr) {
        addEntry(new DebugEntry(tag, msg, tr));
        Crashlytics.log(Log.DEBUG, tag, msg);

        if (tr != null) {
            Crashlytics.logException(tr);
        }
    }

    public static void e(final String tag, final String msg) {
        e(tag, msg, null);
    }

    public static void e(final String tag, final String msg, @Nullable final Throwable tr) {
        addEntry(new ErrorEntry(tag, msg, tr));
        Crashlytics.log(Log.ERROR, tag, msg);

        if (tr != null) {
            Crashlytics.logException(tr);
        }
    }

    public static ArrayList<BaseEntry> getEntries() {
        final ArrayList<BaseEntry> entries;

        synchronized (ENTRIES) {
            entries = new ArrayList<>(ENTRIES);
        }

        Collections.reverse(entries);
        return entries;
    }

    public static void trimEntries() {
        final int newMaxSize = ENTRIES_MAX_SIZE / 2;

        synchronized (ENTRIES) {
            while (ENTRIES.size() >= newMaxSize) {
                ENTRIES.remove(ENTRIES.size() - 1);
            }
        }
    }

    public static void v(final String tag, final String msg) {
        v(tag, msg, null);
    }

    public static void v(final String tag, final String msg, @Nullable final Throwable tr) {
        addEntry(new VerboseEntry(tag, msg, tr));
        Crashlytics.log(Log.VERBOSE, tag, msg);

        if (tr != null) {
            Crashlytics.logException(tr);
        }
    }

    public static void w(final String tag, final String msg) {
        w(tag, msg, null);
    }

    public static void w(final String tag, final String msg, @Nullable final Throwable tr) {
        addEntry(new WarnEntry(tag, msg, tr));
        Crashlytics.log(Log.WARN, tag, msg);

        if (tr != null) {
            Crashlytics.logException(tr);
        }
    }


    public static abstract class BaseEntry {
        private final String mMessage;
        private final String mStackTrace;
        private final String mTag;

        private BaseEntry(final String tag, final String message, @Nullable final Throwable tr) {
            mTag = tag;
            mMessage = message;

            if (tr == null) {
                mStackTrace = null;
            } else {
                mStackTrace = Log.getStackTraceString(tr);
            }
        }

        public abstract int getColor();

        public String getMessage() {
            return mMessage;
        }

        public String getStackTrace() {
            return mStackTrace;
        }

        public String getTag() {
            return mTag;
        }

        public boolean hasStackTrace() {
            return !TextUtils.isEmpty(mStackTrace);
        }
    }


    public static class DebugEntry extends BaseEntry {
        private DebugEntry(final String tag, final String message, @Nullable final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return R.color.debug;
        }
    }


    public static class ErrorEntry extends BaseEntry {
        private ErrorEntry(final String tag, final String message, @Nullable final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return R.color.error;
        }
    }


    public static class VerboseEntry extends BaseEntry {
        private VerboseEntry(final String tag, final String message, @Nullable final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return R.color.verbose;
        }
    }


    public static class WarnEntry extends BaseEntry {
        private WarnEntry(final String tag, final String message, @Nullable final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return android.R.color.holo_orange_dark;
        }
    }

}
