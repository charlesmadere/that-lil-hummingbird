package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class TypefaceTabLayout extends TabLayout {

    private Typeface mTypeface;


    public TypefaceTabLayout(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public TypefaceTabLayout(final Context context, @Nullable final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    @Override
    public void addTab(@NonNull final Tab tab, final boolean setSelected) {
        super.addTab(tab, setSelected);

        final ViewGroup mainView = (ViewGroup) getChildAt(0);
        final ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());
        final int tabViewChildCount = tabView.getChildCount();

        for (int i = 0; i < tabViewChildCount; ++i) {
            final View tabViewChild = tabView.getChildAt(i);

            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface);
            }
        }
    }

    private void parseAttributes(@Nullable final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        int typefaceEntryOrdinal = TypefaceEntry.OPEN_SANS_SEMIBOLD.ordinal();

        if (attrs != null) {
            final Context context = getContext();
            final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View);
            typefaceEntryOrdinal = ta.getInt(R.styleable.View_typeface, typefaceEntryOrdinal);
            ta.recycle();
        }

        mTypeface = TypefaceStore.get(typefaceEntryOrdinal);
    }

}
