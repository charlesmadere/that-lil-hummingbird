package com.charlesmadere.hummingbird.misc;

import android.text.TextUtils;
import android.util.Log;

import com.charlesmadere.hummingbird.R;

import java.util.ArrayList;
import java.util.Collections;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public final class Timber {

    private static final ArrayList<BaseEntry> ENTRIES;
    private static final int ENTRIES_MAX_SIZE;


    static {
        if (MiscUtils.isLowRamDevice()) {
            ENTRIES_MAX_SIZE = 48;
        } else {
            ENTRIES_MAX_SIZE = 256;
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
        addEntry(new DebugEntry(tag, msg));
        getLogger().d(tag, msg);
    }

    public static void d(final String tag, final String msg, final Throwable tr) {
        addEntry(new DebugEntry(tag, msg, tr));
        getLogger().d(tag, msg, tr);
    }

    public static void e(final String tag, final String msg) {
        addEntry(new ErrorEntry(tag, msg));
        getLogger().e(tag, msg);
    }

    public static void e(final String tag, final String msg, final Throwable tr) {
        addEntry(new ErrorEntry(tag, msg, tr));
        getLogger().e(tag, msg, tr);
    }

    public static synchronized ArrayList<BaseEntry> getEntries() {
        final ArrayList<BaseEntry> entries = new ArrayList<>(ENTRIES);
        Collections.reverse(entries);
        return entries;
    }

    private static Logger getLogger() {
        return Fabric.getLogger();
    }

    public static void v(final String tag, final String msg) {
        addEntry(new VerboseEntry(tag, msg));
        getLogger().v(tag, msg);
    }

    public static void v(final String tag, final String msg, final Throwable tr) {
        addEntry(new VerboseEntry(tag, msg, tr));
        getLogger().v(tag, msg, tr);
    }

    public static void w(final String tag, final String msg) {
        addEntry(new WarnEntry(tag, msg));
        getLogger().w(tag, msg);
    }

    public static void w(final String tag, final String msg, final Throwable tr) {
        addEntry(new WarnEntry(tag, msg, tr));
        getLogger().w(tag, msg, tr);
    }


    public static abstract class BaseEntry {
        private final String mMessage;
        private final String mStackTrace;
        private final String mTag;

        private BaseEntry(final String tag, final String message) {
            this(tag, message, null);
        }

        private BaseEntry(final String tag, final String message, final Throwable tr) {
            mTag = tag;
            mMessage = message;

            if (tr == null) {
                mStackTrace = null;
            } else {
                mStackTrace = Log.getStackTraceString(tr);
            }
        }

        public abstract int getColor();

        public abstract char getLevel();

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
        private DebugEntry(final String tag, final String message) {
            super(tag, message);
        }

        private DebugEntry(final String tag, final String message, final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return R.color.debug;
        }

        @Override
        public char getLevel() {
            return 'D';
        }
    }


    public static class ErrorEntry extends BaseEntry {
        private ErrorEntry(final String tag, final String message) {
            super(tag, message);
        }

        private ErrorEntry(final String tag, final String message, final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return R.color.error;
        }

        @Override
        public char getLevel() {
            return 'E';
        }
    }


    public static class VerboseEntry extends BaseEntry {
        private VerboseEntry(final String tag, final String message) {
            super(tag, message);
        }

        private VerboseEntry(final String tag, final String message, final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return R.color.verbose;
        }

        @Override
        public char getLevel() {
            return 'V';
        }
    }


    public static class WarnEntry extends BaseEntry {
        private WarnEntry(final String tag, final String message) {
            super(tag, message);
        }

        private WarnEntry(final String tag, final String message, final Throwable tr) {
            super(tag, message, tr);
        }

        @Override
        public int getColor() {
            return android.R.color.holo_orange_dark;
        }

        @Override
        public char getLevel() {
            return 'W';
        }
    }

}
