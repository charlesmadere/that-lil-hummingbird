package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityManagerCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;

import java.text.NumberFormat;

public final class MiscUtils {

    private static final long MINUTE_IN_SECONDS = 60L;
    private static final long HOUR_IN_SECONDS = MINUTE_IN_SECONDS * 60L;
    private static final long DAY_IN_SECONDS = HOUR_IN_SECONDS * 24L;
    private static final long WEEK_IN_SECONDS = DAY_IN_SECONDS * 7L;
    private static final long MONTH_IN_SECONDS = DAY_IN_SECONDS * 30L;
    private static final long YEAR_IN_SECONDS = DAY_IN_SECONDS * 365L;


    public static void closeKeyboard(final Activity activity) {
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }

        view.clearFocus();
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Activity getActivity(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            final ContextWrapper contextWrapper = (ContextWrapper) context;
            return getActivity(contextWrapper.getBaseContext());
        } else {
            throw new RuntimeException("context isn't connected to an Activity");
        }
    }

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

    public static CharSequence getElapsedTime(final Resources res, long seconds) {
        if (seconds <= 0) {
            return res.getText(R.string.none);
        }

        final NumberFormat numberFormat = NumberFormat.getInstance();
        final SpannableStringBuilder string = new SpannableStringBuilder();

        final int years = (int) (seconds / YEAR_IN_SECONDS);
        if (years >= 1) {
            seconds = seconds - (years * YEAR_IN_SECONDS);
            string.append(res.getQuantityString(R.plurals.x_years, years,
                    numberFormat.format(years)));
        }

        final int months = (int) (seconds / MONTH_IN_SECONDS);
        if (months >= 1) {
            seconds = seconds - (months * MONTH_IN_SECONDS);

            if (!TextUtils.isEmpty(string)) {
                string.append(res.getText(R.string.delimiter));
            }

            string.append(res.getQuantityString(R.plurals.x_months, months,
                    numberFormat.format(months)));
        }

        final int weeks = (int) (seconds / WEEK_IN_SECONDS);
        if (weeks >= 1) {
            seconds = seconds - (weeks * WEEK_IN_SECONDS);

            if (!TextUtils.isEmpty(string)) {
                string.append(res.getText(R.string.delimiter));
            }

            string.append(res.getQuantityString(R.plurals.x_weeks, weeks,
                    numberFormat.format(weeks)));
        }

        final int days = (int) (seconds / DAY_IN_SECONDS);
        if (days >= 1) {
            seconds = seconds - (days * DAY_IN_SECONDS);

            if (!TextUtils.isEmpty(string)) {
                string.append(res.getText(R.string.delimiter));
            }

            string.append(res.getQuantityString(R.plurals.x_days, days,
                    numberFormat.format(days)));
        }

        final int hours = (int) (seconds / HOUR_IN_SECONDS);
        if (hours >= 1) {
            seconds = seconds - (hours * HOUR_IN_SECONDS);

            if (!TextUtils.isEmpty(string)) {
                string.append(res.getText(R.string.delimiter));
            }

            string.append(res.getQuantityString(R.plurals.x_hours, hours,
                    numberFormat.format(hours)));
        }

        final int minutes = (int) (seconds / MINUTE_IN_SECONDS);
        if (minutes >= 1) {
            seconds = seconds - (minutes * MINUTE_IN_SECONDS);

            if (!TextUtils.isEmpty(string)) {
                string.append(res.getText(R.string.delimiter));
            }

            string.append(res.getQuantityString(R.plurals.x_minutes, minutes,
                    numberFormat.format(minutes)));
        }

        if (seconds >= 1L) {
            if (!TextUtils.isEmpty(string)) {
                string.append(res.getText(R.string.delimiter));
            }

            string.append(res.getQuantityString(R.plurals.x_seconds, (int) seconds,
                    numberFormat.format(seconds)));
        }

        return string;
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
            final ActivityManager am = (ActivityManager) ThatLilHummingbird.get()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            return ActivityManagerCompat.isLowRamDevice(am);
        } else {
            return true;
        }
    }

    public static void openKeyboard(final Context context, final View view) {
        view.requestFocus();
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static void openUrl(final Activity activity, final String url) {
        if (activity == null) {
            throw new IllegalArgumentException("activity can't be null");
        }

        if (TextUtils.isEmpty(url) || TextUtils.getTrimmedLength(url) == 0) {
            return;
        }

        final CustomTabsIntent intent = new CustomTabsIntent.Builder()
                .setToolbarColor(MiscUtils.getAttrColor(activity, R.attr.colorPrimary))
                .build();

        intent.launchUrl(activity, Uri.parse(url));
    }

    public static void openUrl(final Context context, final String url) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }

        openUrl(MiscUtils.getActivity(context), url);
    }

}
