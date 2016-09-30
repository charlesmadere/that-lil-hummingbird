package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.AppNewsStatus;
import com.charlesmadere.hummingbird.models.TypefaceEntry;
import com.charlesmadere.hummingbird.preferences.Preference;
import com.charlesmadere.hummingbird.preferences.Preferences;

public class AppNewsDrawerTextView extends AppCompatTextView implements
        Preference.OnPreferenceChangeListener<AppNewsStatus> {

    public AppNewsDrawerTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AppNewsDrawerTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (isInEditMode()) {
            return;
        }

        Preferences.Misc.AppNewsAvailability.addListener(this);
        refreshImportantNewsBadge();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (isInEditMode()) {
            return;
        }

        Preferences.Misc.AppNewsAvailability.removeListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        Preferences.Misc.AppNewsAvailability.addListener(this);
        refreshImportantNewsBadge();
    }

    @Override
    public void onPreferenceChange(final Preference<AppNewsStatus> preference) {
        refreshImportantNewsBadge();
    }

    private void refreshImportantNewsBadge() {
        final AppNewsStatus appNewsStatus = Preferences.Misc.AppNewsAvailability.get();

        final int badge;
        final Typeface typeface;

        if (appNewsStatus != null && appNewsStatus.isImportantNewsAvailable()) {
            badge = R.drawable.badge;
            typeface = TypefaceStore.get(TypefaceEntry.OPEN_SANS_BOLD);
        } else {
            badge = 0;
            typeface = TypefaceStore.get(TypefaceEntry.OPEN_SANS_SEMIBOLD);
        }

        setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, badge, 0);
        setTypeface(typeface);
    }

}
