package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class MiscUtils {

    private static final String TAG = "MiscUtils";

    private static final long MINUTE_IN_SECONDS = TimeUnit.MINUTES.toSeconds(1L);
    private static final long HOUR_IN_SECONDS = TimeUnit.HOURS.toSeconds(1L);
    private static final long DAY_IN_SECONDS = TimeUnit.DAYS.toSeconds(1L);
    private static final long WEEK_IN_SECONDS = TimeUnit.DAYS.toSeconds(7L);
    private static final long MONTH_IN_SECONDS = TimeUnit.DAYS.toSeconds(30L);
    private static final long YEAR_IN_SECONDS = TimeUnit.DAYS.toSeconds(365L);


    public static boolean booleanEquals(@Nullable final Boolean lhs, @Nullable final Boolean rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equals(rhs);
    }

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

    public static <T> void exclusiveAdd(final List<T> list, @Nullable final List<T> elementsToAdd) {
        if (elementsToAdd == null || elementsToAdd.isEmpty()) {
            return;
        }

        for (final T elementToAdd : elementsToAdd) {
            if (!list.contains(elementToAdd)) {
                list.add(elementToAdd);
            }
        }
    }

    public static Activity getActivity(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context parameter can't be null");
        } else {
            final Activity activity = optActivity(context);

            if (activity == null) {
                throw new RuntimeException("context isn't connected to an Activity");
            }

            return activity;
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

    @ColorInt
    public static int getDrawableColor(final Context context, @Nullable final Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            return ((ColorDrawable) drawable).getColor();
        } else {
            return ContextCompat.getColor(context, R.color.transparent);
        }
    }

    @ColorInt
    public static int getDrawableColor(final View view, @Nullable final Drawable drawable) {
        return getDrawableColor(view.getContext(), drawable);
    }

    public static CharSequence getElapsedTime(final Resources res, long seconds) {
        if (seconds <= 0L) {
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

    @Nullable
    public static String[] getGroupLogos(@Nullable final String logo) {
        if (!isValidArtwork(logo)) {
            return null;
        }

        final String replace;
        if (logo.contains('/' + Constants.IMAGE_TEMPLATE_MEDIUM + '.')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_MEDIUM + '.';
        } else if (logo.contains('/' + Constants.IMAGE_TEMPLATE_SMALL + '.')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_SMALL + '.';
        } else if (logo.contains('/' + Constants.IMAGE_TEMPLATE_THUMB + '.')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_THUMB + '.';
        } else if (logo.contains('/' + Constants.IMAGE_TEMPLATE_THUMB_SMALL + '.')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_THUMB_SMALL + '.';
        } else if (logo.contains('/' + Constants.IMAGE_TEMPLATE_ORIGINAL + '.')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_ORIGINAL + '.';
        } else {
            return new String[] { logo };
        }

        return new String[] { logo.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_THUMB + '.'),
                logo.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_THUMB_SMALL + '.'),
                logo.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_SMALL + '.'),
                logo.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_MEDIUM + '.'),
                logo.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_ORIGINAL + '.') };
    }

    public static LayerDrawable getStatusBarScrim(final Context context,
            @Nullable final Drawable statusBarBackground) {
        final int statusBarScrimColor = getDrawableColor(context, statusBarBackground);
        return new LayerDrawable(new Drawable[] { new ColorDrawable(statusBarScrimColor),
                new ColorDrawable(ContextCompat.getColor(context, R.color.translucent)) } );
    }

    @Nullable
    public static String[] getUserAvatars(@Nullable final String avatar) {
        if (!isValidArtwork(avatar)) {
            return null;
        }

        final String replace;
        if (avatar.contains('/' + Constants.IMAGE_TEMPLATE_MEDIUM + '/')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_MEDIUM + '/';
        } else if (avatar.contains('/' + Constants.IMAGE_TEMPLATE_SMALL + '/')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_SMALL + '/';
        } else if (avatar.contains('/' + Constants.IMAGE_TEMPLATE_THUMB + '/')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_THUMB + '/';
        } else if (avatar.contains('/' + Constants.IMAGE_TEMPLATE_THUMB_SMALL + '/')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_THUMB_SMALL + '/';
        } else if (avatar.contains('/' + Constants.IMAGE_TEMPLATE_ORIGINAL + '/')) {
            replace = '/' + Constants.IMAGE_TEMPLATE_ORIGINAL + '/';
        } else {
            return new String[] { avatar };
        }

        return new String[] { avatar.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_THUMB + '/'),
                avatar.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_THUMB_SMALL + '/'),
                avatar.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_SMALL + '/'),
                avatar.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_MEDIUM + '/'),
                avatar.replaceFirst(replace, '/' + Constants.IMAGE_TEMPLATE_ORIGINAL + '/') };
    }

    public static int integerCompare(final int lhs, final int rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static boolean integerEquals(@Nullable final Integer lhs, @Nullable final Integer rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equals(rhs);
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

    public static boolean isValidArtwork(final String url) {
        if (TextUtils.isEmpty(url) || TextUtils.getTrimmedLength(url) == 0) {
            return false;
        }

        for (final String missingArtwork : Constants.MISSING_ARTWORK) {
            if (missingArtwork.equalsIgnoreCase(url)) {
                return false;
            }
        }

        return true;
    }

    public static void openKeyboard(final Context context, final View view) {
        view.requestFocus();
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static void openUrl(final Activity activity, final String url) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter can't be null");
        }

        if (TextUtils.isEmpty(url) || TextUtils.getTrimmedLength(url) == 0) {
            return;
        }

        try {
            final CustomTabsIntent intent = new CustomTabsIntent.Builder()
                    .setToolbarColor(MiscUtils.getAttrColor(activity, R.attr.colorPrimary))
                    .build();

            intent.launchUrl(activity, Uri.parse(url));
            return;
        } catch (final ActivityNotFoundException e) {
            Timber.e(TAG, "Unable to open Chrome Custom Tab to URL: \"" + url + '"', e);
        }

        try {
            final Intent intent = new Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .setData(Uri.parse(url));

            activity.startActivity(intent);
            return;
        } catch (final ActivityNotFoundException e) {
            Timber.e(TAG, "Unable to open browser to URL: \"" + url + '"', e);
        }

        Toast.makeText(activity, R.string.unable_to_open_link, Toast.LENGTH_LONG).show();
    }

    public static void openUrl(final Context context, final String url) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }

        openUrl(MiscUtils.getActivity(context), url);
    }

    @Nullable
    public static Activity optActivity(@Nullable final Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            final ContextWrapper contextWrapper = (ContextWrapper) context;
            return optActivity(contextWrapper.getBaseContext());
        } else {
            return null;
        }
    }

    @Nullable
    public static <T> ArrayList<T> toArrayList(@Nullable final Collection<T> collection) {
        if (collection == null) {
            return null;
        } else if (collection instanceof ArrayList) {
            return (ArrayList<T>) collection;
        } else {
            return new ArrayList<>(collection);
        }
    }

}
