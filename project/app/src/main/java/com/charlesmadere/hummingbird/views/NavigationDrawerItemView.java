package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class NavigationDrawerItemView extends AppCompatTextView {

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
        setTextAndIcon();
    }

    @Override
    public void setActivated(final boolean activated) {
        super.setActivated(activated);
        setTextAndIcon();
    }

    public void setOnClickListener(@Nullable final OnClickListener l) {
        if (l == null) {
            setClickable(false);
        } else {
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    l.onClick(NavigationDrawerItemView.this);
                }
            });
        }
    }

    @Override
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        setTextAndIcon();
    }

    @SuppressWarnings("ResourceAsColor")
    private void setTextAndIcon() {
        if (!isInEditMode()) {
            setTypeface(isSelected() ? TypefaceStore.get(TypefaceEntry.OPEN_SANS_BOLD) :
                    TypefaceStore.get(TypefaceEntry.OPEN_SANS_SEMIBOLD));
        }

        final int color = isSelected() ? MiscUtils.getAttrColor(getContext(), R.attr.colorAccent)
                : ContextCompat.getColor(getContext(), R.color.white);

        setTextColor(color);
        setText(mEntry.getTextResId());

        final int badge = isActivated() ? R.drawable.badge : 0;
        setCompoundDrawablesRelativeWithIntrinsicBounds(mEntry.getIconResId(), 0, badge, 0);
    }


    public enum Entry {
        ANIME_LIBRARY(R.drawable.ic_anime_library, R.string.anime_library),
        APP_NEWS(R.drawable.ic_new_releases, R.string.app_news),
        NEWS_FEED(R.drawable.ic_feed, R.string.news_feed),
        MANGA_LIBRARY(R.drawable.ic_manga_library, R.string.manga_library),
        NOTIFICATIONS(R.drawable.ic_notifications, R.string.notifications),
        SETTINGS(R.drawable.ic_settings, R.string.settings);

        @DrawableRes
        private final int mIconResId;

        @StringRes
        private final int mTextResId;


        Entry(@DrawableRes final int iconResId, @StringRes final int textResId) {
            mIconResId = iconResId;
            mTextResId = textResId;
        }

        @DrawableRes
        public int getIconResId() {
            return mIconResId;
        }

        @StringRes
        public int getTextResId() {
            return mTextResId;
        }
    }


    public interface OnClickListener {
        void onClick(final NavigationDrawerItemView v);
    }

}
