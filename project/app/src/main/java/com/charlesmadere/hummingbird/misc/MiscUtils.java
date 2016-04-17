package com.charlesmadere.hummingbird.misc;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityManagerCompat;

import com.charlesmadere.hummingbird.Hummingbird;
import com.charlesmadere.hummingbird.R;

public final class MiscUtils {

    public static int getStatusBarHeight(final Resources res) {
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId == 0) {
            resourceId = R.dimen.status_bar_height;
        }

        return res.getDimensionPixelSize(resourceId);
    }

    public static int integerCompare(final int lhs, final int rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static boolean isLowRamDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ActivityManager am = (ActivityManager) Hummingbird.get()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            return ActivityManagerCompat.isLowRamDevice(am);
        } else {
            return true;
        }
    }

}
