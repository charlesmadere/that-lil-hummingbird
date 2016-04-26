package com.charlesmadere.hummingbird.misc;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityManagerCompat;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import com.charlesmadere.hummingbird.Hummingbird;
import com.charlesmadere.hummingbird.R;

public final class MiscUtils {

    @ColorInt
    public static int getAttrColor(final Context context, @AttrRes final int colorResId) {
        final TypedArray ta = context.obtainStyledAttributes(new int[] { colorResId } );

        if (ta.hasValue(0)) {
            final int color = ta.getColor(0, 0);
            ta.recycle();

            if (color == 0) {
                throw new RuntimeException("unable to find colorResId: " + colorResId);
            }

            return color;
        } else {
            throw new RuntimeException("unable to find colorResId: " + colorResId);
        }
    }

    public static int getNavigationBarHeight(final Resources res) {
        if (KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK) &&
                !Build.FINGERPRINT.contains("generic")) {
            return 0;
        }

        int resourceId;

        if (res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            resourceId = res.getIdentifier("navigation_bar_height_landscape", "dimen", "android");
        } else {
            resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        }

        if (resourceId == 0) {
            resourceId = R.dimen.navigation_bar_height;
        }

        return res.getDimensionPixelSize(resourceId);
    }

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
