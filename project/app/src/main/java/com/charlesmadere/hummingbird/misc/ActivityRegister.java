package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public final class ActivityRegister {

    private static final LinkedList<WeakReference<Activity>> ATTACHMENTS = new LinkedList<>();


    public static synchronized void attach(final Activity activity) {
        boolean attach = true;

        if (!ATTACHMENTS.isEmpty()) {
            final Iterator<WeakReference<Activity>> iterator = ATTACHMENTS.iterator();

            while (iterator.hasNext()) {
                final Activity a = iterator.next().get();

                if (a == null || a.isDestroyed()) {
                    iterator.remove();
                } else if (a == activity) {
                    attach = false;
                }
            }
        }

        if (attach) {
            ATTACHMENTS.add(new WeakReference<>(activity));
        }
    }

    public static synchronized void detach(final Activity activity) {
        final Iterator<WeakReference<Activity>> iterator = ATTACHMENTS.iterator();

        while (iterator.hasNext()) {
            final Activity a = iterator.next().get();

            if (a == null || a.isDestroyed() || a == activity) {
                iterator.remove();
            }
        }
    }

    @Nullable
    public static synchronized ArrayList<Activity> get() {
        if (ATTACHMENTS.isEmpty()) {
            return null;
        }

        final ArrayList<Activity> activities = new ArrayList<>();
        final Iterator<WeakReference<Activity>> iterator = ATTACHMENTS.iterator();

        while (iterator.hasNext()) {
            final Activity activity = iterator.next().get();

            if (activity == null || activity.isDestroyed()) {
                iterator.remove();
            } else {
                activities.add(activity);
            }
        }

        if (activities.isEmpty()) {
            return null;
        } else {
            return activities;
        }
    }

}
