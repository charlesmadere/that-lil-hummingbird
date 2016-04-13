package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;

public class NavigationDrawerItemView extends SelectableTextView {

    private Entry mEntry;


    public NavigationDrawerItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public NavigationDrawerItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    public Entry getEntry() {
        return mEntry;
    }

    private void initialize(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.NavigationDrawerItemView);

        if (ta.hasValue(R.styleable.NavigationDrawerItemView_entry)) {
            final int entry = ta.getInt(R.styleable.NavigationDrawerItemView_entry, -1);

            if (entry >= 0 && entry < Entry.values().length) {
                mEntry = Entry.values()[entry];
            } else {
                throw new IllegalArgumentException("entry is an invalid value: " + entry);
            }
        } else {
            throw new IllegalStateException("entry must be set");
        }

        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateTextAndIcon();
    }

    public void setOnNavigationDrawerItemViewClickListener(
            @Nullable final OnNavigationDrawerItemViewClickListener l) {
        if (l == null) {
            setClickable(false);
        } else {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    l.onNavigationDrawerItemViewClick(NavigationDrawerItemView.this);
                }
            });
        }
    }

    @Override
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        updateTextAndIcon();
    }

    private void updateTextAndIcon() {
        final int colorResId = isSelected() ? R.color.colorAccent : R.color.white;
        final int color = ContextCompat.getColor(getContext(), colorResId);
        setTextColor(color);
        setText(mEntry.getTextResId());

        Drawable icon = ContextCompat.getDrawable(getContext(), mEntry.getIconResId());
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(icon, color);
        setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
    }


    public enum Entry {
        HOME(R.drawable.ic_home_white_24dp, R.string.home),
        HUMMINGBIRD_ON_THE_WEB(R.drawable.ic_open_in_browser_white_24dp,
                R.string.hummingbird_on_the_web),
        SEARCH(R.drawable.ic_search_white_24dp, R.string.search),
        SETTINGS(R.drawable.ic_settings_white_24dp, R.string.settings);

        private final int mIconResId;
        private final int mTextResId;

        Entry(@DrawableRes final int iconResId, @StringRes final int textResId) {
            mIconResId = iconResId;
            mTextResId = textResId;
        }

        public int getIconResId() {
            return mIconResId;
        }

        public int getTextResId() {
            return mTextResId;
        }
    }


    public interface OnNavigationDrawerItemViewClickListener {
        void onNavigationDrawerItemViewClick(final NavigationDrawerItemView v);
    }

}
